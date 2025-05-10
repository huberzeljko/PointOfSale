import SwiftUI

struct ClearableTextField: View {
  let placeholder: String
  @Binding var text: String
  var leadingIcon: String? = nil
  var keyboardType: UIKeyboardType = .default
  var onCommit: () -> Void = {}
  var onClear: () -> Void = {}

  var body: some View {
    HStack {
      if let iconName = leadingIcon {
        Image(systemName: iconName)
          .foregroundColor(.gray)
      }

      TextField(placeholder, text: $text, onCommit: onCommit)
        .keyboardType(keyboardType)
        .autocorrectionDisabled()

      if !text.isEmpty {
        Button { text = ""; onClear() } label: {
          Image(systemName: "xmark.circle.fill")
        }
        .foregroundColor(.gray)
      }
    } .padding(8)
      .background(Color(.secondarySystemBackground))
      .cornerRadius(8)
  }
}

