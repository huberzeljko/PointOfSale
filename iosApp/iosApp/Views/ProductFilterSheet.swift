import SwiftUI

struct ProductFilterSheet: View {
  @Binding var minText: String
  @Binding var maxText: String
  var onApply: () -> Void

  @Environment(\.dismiss) var dismiss

  var body: some View {
    NavigationStack {
      VStack(alignment: .leading, spacing: 20) {
        VStack(spacing: 12) {
          VStack(alignment: .leading, spacing: 4) {
            Text("Min Price")
              .font(.subheadline)
              .foregroundColor(.secondary)
            ClearableTextField(
              placeholder: "Min Price",
              text: $minText,
              keyboardType: .decimalPad
            )
          }
          VStack(alignment: .leading, spacing: 4) {
            Text("Max Price")
              .font(.subheadline)
              .foregroundColor(.secondary)
            ClearableTextField(
              placeholder: "Max Price",
              text: $maxText,
              keyboardType: .decimalPad
            )
          }
        }

        Spacer()
      }
      .padding()
      .navigationTitle("Filters")
      .toolbar {
        ToolbarItem(placement: .cancellationAction) {
          Button("Cancel") { dismiss() }
        }
        ToolbarItem(placement: .confirmationAction) {
          Button("Apply") { onApply(); dismiss() }
        }
      }
    }
  }
}
