import Combine
import SwiftUI
import Shared

struct ContentView: View {
  var body: some View {
    ProductListScreen()
      .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
      .ignoresSafeArea(.all, edges: .bottom)
  }
}

struct ContentView_Previews: PreviewProvider {
  static var previews: some View {
    ContentView()
  }
}
