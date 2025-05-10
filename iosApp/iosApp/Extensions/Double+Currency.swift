import Foundation

extension Double {
  var asCurrency: String {
    let fmt = NumberFormatter()
    fmt.numberStyle = .currency
    fmt.currencySymbol = "€"
    fmt.minimumFractionDigits = 2
    fmt.maximumFractionDigits = 2
    return fmt.string(from: NSNumber(value: self)) ?? String(format: "%.2f", self)
  }
}
