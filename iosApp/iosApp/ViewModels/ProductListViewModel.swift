import Combine
import SwiftUI
import Shared
import Observation

fileprivate let PAGE_SIZE: Int32 = 20

@Observable
@MainActor
final class ProductListViewModel: ObservableObject {
  private let service: ProductService

  var query: String = "" {
    didSet(oldQuery) {
      guard oldQuery != query else { return }
      debounceReload()
    }
  }

  var sortOption: ProductSortOption = .nameAsc {
    didSet(oldOption) {
      guard oldOption != sortOption else { return }
      Task { await reload() }
    }
  }

  var minPriceText: String = ""
  var maxPriceText: String = ""
  var showFilters: Bool = false
  private var page = 1
  private var debounceTask: Task<Void, Error>?
  private(set) var products: ProductListState = .idle
  private(set) var hasMore = false

  init(service: ProductService = ProductService.companion.default_) {
    self.service = service
  }

  func reload() async {
    page = 1
    await load(nextPage: false)
  }

  func loadMore() async {
    guard hasMore else { return }
    page += 1
    await load(nextPage: true)
  }

  private func debounceReload() {
    products.cancelIfLoading()
    debounceTask?.cancel()

    debounceTask = Task { [weak self] in
      try await Task.sleep(nanoseconds: 300 * 1_000_000)
      guard let self = self, !Task.isCancelled else { return }
      await self.reload()
    }
  }

  private func load(nextPage: Bool) async {
    products.cancelIfLoading()
    debounceTask?.cancel()

    let filter = ProductsFilter(
      searchQuery: query,
      minPrice: Double(minPriceText).map { KotlinDouble(double: $0) },
      maxPrice: Double(maxPriceText).map { KotlinDouble(double: $0) },
      paging: Paging(page: Int32(page), pageSize: PAGE_SIZE)
    )

    let loadTask = Task { [weak self] in
      guard let self = self else { return }

      do {
        let fetched = try await service.getProducts(filter: filter, sorter: sortOption.sorting)
        guard !Task.isCancelled else { return }

        hasMore = fetched.count == PAGE_SIZE
        products = .loaded((products.previousValue ?? []) + fetched)
      } catch {
        guard !Task.isCancelled else { return }

        products = .failed(error)
      }
    }

    products = .loading(
      previous: nextPage ? products.previousValue : nil,
      task: loadTask
    )
  }
}

