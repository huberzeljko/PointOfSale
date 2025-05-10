import Shared

extension ProductListViewModel {
  enum ProductListState {
    case idle
    case loading(previous: [Product]?, task: Task<Void, Never>)
    case loaded([Product])
    case failed(Error)
  }
}

extension ProductListViewModel.ProductListState {
  func cancelIfLoading() {
    switch self {
    case .loading(_, let task):
      task.cancel()
    default:
      return
    }
  }

  var previousValue: [Product]? {
    switch self {
    case .loading(let value, _):
      return value
    case .loaded(let value):
      return value
    default:
      return nil
    }
  }

  var isLoading: Bool {
    switch self {
    case .loading(_, _):
      return true
    default:
      return false
    }
  }
}
