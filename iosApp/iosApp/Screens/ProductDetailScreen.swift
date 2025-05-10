import SwiftUI
import Shared

struct ProductDetailView: View {
  let product: Product
  
  var body: some View {
    VStack(spacing: 16) {
      Text(product.name)
        .font(.title2)
        .bold()
      Text(product.formattedPrice)
        .font(.title3)
        .foregroundColor(.secondary)
      Spacer()
    }
    .padding()
    .navigationTitle("Details")
  }
}
