import SwiftUI

struct Pill: Identifiable {
  let id: String
  let title: String
  let isSelected: Bool
  let onTap: () -> Void
}

struct PillScrollView: View {
  let pills: [Pill]

  var body: some View {
    ScrollView(.horizontal, showsIndicators: false) {
      HStack(spacing: 12) {
        ForEach(pills) { pill in
          Text(pill.title)
            .fontWeight(.medium)
            .padding(.vertical, 5)
            .padding(.horizontal, 10)
            .background(pill.isSelected
                        ? Color.accentColor
                        : Color(.tertiarySystemBackground))
            .foregroundColor(pill.isSelected
                             ? .white
                             : .primary)
            .cornerRadius(8)
            .onTapGesture { pill.onTap() }
        }
      }
      .padding(.horizontal)
    }
  }
}
