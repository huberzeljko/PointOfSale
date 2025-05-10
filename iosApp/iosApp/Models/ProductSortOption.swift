import Shared

enum ProductSortOption: String, CaseIterable, Identifiable {
  case nameAsc    = "A → Z"
  case nameDesc   = "Z → A"
  case priceAsc   = "Cheapest"
  case priceDesc  = "Most Expensive"

  var id: String { rawValue }

  var sorting: ProductsSorter {
    switch self {
    case .nameAsc:  return .init(by: .name, descending: false)
    case .nameDesc: return .init(by: .name, descending: true)
    case .priceAsc: return .init(by: .price, descending: false)
    case .priceDesc:return .init(by: .price, descending: true)
    }
  }
}
