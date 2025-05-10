extension ProductListViewModel {
  var activeFilterPills: [Pill] {
    var result: [Pill] = []

    if let min = Double(minPriceText) {
      result.append(.init(
        id: "min",
        title: "Min $\(min.asCurrency)",
        isSelected: true
      ) {
        self.minPriceText = ""
        Task { await self.reload() }
      })
    }

    if let max = Double(maxPriceText) {
      result.append(.init(
        id: "max",
        title: "Max $\(max.asCurrency)",
        isSelected: true
      ) {
        self.maxPriceText = ""
        Task { await self.reload() }
      })
    }

    return result
  }
}
