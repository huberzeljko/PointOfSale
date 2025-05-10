import Combine
import SwiftUI
import Shared

struct ProductListScreen: View {
  @State private var viewModel = ProductListViewModel()

  var body: some View {
    NavigationStack {
      VStack(spacing: 0) {
        searchView
        sortView
        activeFiltersView
        contentView
      }
      .frame(maxHeight: .infinity, alignment: .top)
      .sheet(isPresented: $viewModel.showFilters) {
        ProductFilterSheet(
          minText: $viewModel.minPriceText,
          maxText: $viewModel.maxPriceText,
          onApply: { Task { await viewModel.reload() } }
        )
      }
      .task {
        if (viewModel.products.previousValue == nil) {
          await viewModel.reload()
        }
      }
    }
    .navigationTitle("Products")
    .navigationBarTitleDisplayMode(.inline)
  }

  private var searchView: some View {
    HStack {
      ClearableTextField(
        placeholder: "Search products",
        text: $viewModel.query,
        leadingIcon: "magnifyingglass"
      )

      Button { viewModel.showFilters = true } label: {
        Image(systemName: "slider.horizontal.3")
          .padding(10)
          .background(Color(.secondarySystemBackground))
          .cornerRadius(10)
      }
    }
    .padding()
  }

  private var sortView: some View {
    PillScrollView(pills: ProductSortOption.allCases.map { option in
      Pill(
        id: option.id,
        title: option.rawValue,
        isSelected: viewModel.sortOption == option
      ) {
        viewModel.sortOption = option
      }
    })
    .padding(.bottom, 8)
  }

  @ViewBuilder
  private var contentView: some View {
    switch viewModel.products {
    case .idle:
      EmptyView()

    case .loading, .loaded:
      productsListView

    case .failed:
      VStack(spacing: 12) {
        Text("Oops, something went wrong!")
          .multilineTextAlignment(.center)
        Button("Try again") { Task { await viewModel.reload() } }
      }
      .frame(maxWidth: .infinity, maxHeight: .infinity)
      .padding()
    }
  }

  @ViewBuilder
  private var productsListView: some View {
    let products = viewModel.products.previousValue ?? []
    let loading = viewModel.products.isLoading

    if !loading && products.isEmpty && !viewModel.hasMore {
      Text("No products found.")
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    } else {
      List {
        Section {
          ForEach(products, id: \.id) { product in
            NavigationLink(value: product) {
              ProductRow(product: product)
            }
            .task {
              if product.id == products.last?.id {
                await viewModel.loadMore()
              }
            }
          }
        } footer: {
          if loading {
            HStack {
              Spacer()
              Text("Loading...")
                .font(.subheadline)
              Spacer()
            }
            .padding(.vertical, 8)
            .listRowSeparator(.hidden)
            .frame(height: 44)
          }
        }
      }
      .listStyle(.plain)
      .refreshable { await viewModel.reload() }
      .navigationDestination(for: Product.self) { ProductDetailView(product: $0) }
    }
  }

  private var activeFiltersView: some View {
    PillScrollView(pills: viewModel.activeFilterPills)
      .padding(.bottom, 8)
  }
}

private struct ProductRow: View {
  let product: Product
  var body: some View {
    HStack {
      Text(product.name).font(.body)
      Spacer()
      Text(product.formattedPrice)
        .font(.subheadline).foregroundColor(.secondary)
    }
    .padding(.vertical, 8)
  }
}
