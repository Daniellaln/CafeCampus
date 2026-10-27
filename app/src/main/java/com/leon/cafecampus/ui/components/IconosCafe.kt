package com.leon.cafecampus.ui.components
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.unit.dp
// Original geometry. Editable SVG sources live in design/icons-svg.
object IconosCafe {
    private fun crear(nombre: String,trazo: String,relleno: Boolean): ImageVector = ImageVector.Builder(nombre,24.dp,24.dp,24f,24f).apply {
        addPath(PathParser().parsePathString(trazo).toNodes(),fill=if(relleno) SolidColor(Color.Black) else null,stroke=SolidColor(Color.Black),strokeLineWidth=1.7f,strokeLineCap=StrokeCap.Round,strokeLineJoin=StrokeJoin.Round)
    }.build()
    val Search: ImageVector by lazy { crear("Search","M16 16 L21 21 M17.5 10.5 A7 7 0 1 1 3.5 10.5 A7 7 0 1 1 17.5 10.5",false) }
    val ShoppingBag: ImageVector by lazy { crear("ShoppingBag","M5 7.5 H19 L20 20.5 H4 Z M8.5 9 V6 A3.5 3.5 0 0 1 15.5 6 V9",false) }
    val Home: ImageVector by lazy { crear("Home","M3 10.5 L12 3 L21 10.5 M5.5 9 V21 H9.5 V14 H14.5 V21 H18.5 V9",false) }
    val ReceiptLong: ImageVector by lazy { crear("ReceiptLong","M5 3 H19 V21 L15.5 19 L12 21 L8.5 19 L5 21 Z M8.5 7 H15.5 M8.5 11 H15.5 M8.5 15 H12.5",false) }
    val PersonOutline: ImageVector by lazy { crear("PersonOutline","M16 7 A4 4 0 1 1 8 7 A4 4 0 1 1 16 7 M4.5 21 V19 C4.5 12 19.5 12 19.5 19 V21 Z",false) }
    val BarChart: ImageVector by lazy { crear("BarChart","M4 21 V4 M4 21 H22 M8 17 V11 H11 V17 Z M14 17 V5 H17 V17 Z M20 17 V9",false) }
    val Settings: ImageVector by lazy { crear("Settings","M9 3 H15 L15.7 6 L18 7.3 L21 6.5 L23 11 L20.5 13 L20.5 15.5 L22 18 L18.5 21 L16 19.5 L13.5 20 L12 23 L7.5 22 L7 19 L4.5 17.5 L2 18 L.8 13.5 L3.5 12 L4 9.5 L2.5 7 L6 4 L8.5 5.5 Z M16.5 13 A4.5 4.5 0 1 1 7.5 13 A4.5 4.5 0 1 1 16.5 13",false) }
    val Coffee: ImageVector by lazy { crear("Coffee","M4 8 H17 V14 C17 21 4 21 4 14 Z M17 9 H19 C24 9 23 15 17 15 M3 22 H19 M7 2 C5 4 9 4 7 6 M12 1 C10 3 14 3 12 5",false) }
    val LocalDrink: ImageVector by lazy { crear("LocalDrink","M5 3 H19 L17 21 H7 Z M6 7 H18 M13 3 L17 1",false) }
    val BakeryDining: ImageVector by lazy { crear("BakeryDining","M3 16 C2 10 7 4 12 5 C17 4 22 10 21 16 L17 18 L14 15 L10 15 L7 18 Z M7 7 L10 15 M17 7 L14 15 M3 12 L7 18 M21 12 L17 18",false) }
    val FavoriteBorder: ImageVector by lazy { crear("FavoriteBorder","M12 21 C9 18 3 14 3 8.5 C3 2 10 1.5 12 6 C14 1.5 21 2 21 8.5 C21 14 15 18 12 21 Z",false) }
    val Favorite: ImageVector by lazy { crear("Favorite","M12 21 C9 18 3 14 3 8.5 C3 2 10 1.5 12 6 C14 1.5 21 2 21 8.5 C21 14 15 18 12 21 Z",true) }
    val Tune: ImageVector by lazy { crear("Tune","M3 6 H8 M12 6 H21 M3 12 H13 M17 12 H21 M3 18 H6 M10 18 H21 M8 3 V9 H12 V3 Z M13 9 V15 H17 V9 Z M6 15 V21 H10 V15 Z",false) }
    val ExpandMore: ImageVector by lazy { crear("ExpandMore","M5 9 L12 16 L19 9",false) }
    val ArrowForward: ImageVector by lazy { crear("ArrowForward","M4 12 H20 M13 5 L20 12 L13 19",false) }
    val ArrowBack: ImageVector by lazy { crear("ArrowBack","M20 12 H4 M11 5 L4 12 L11 19",false) }
    val Close: ImageVector by lazy { crear("Close","M5 5 L19 19 M19 5 L5 19",false) }
    val Check: ImageVector by lazy { crear("Check","M4 12 L9 17 L20 6",false) }
    val CheckCircle: ImageVector by lazy { crear("CheckCircle","M21 12 A9 9 0 1 1 3 12 A9 9 0 1 1 21 12 M7 12 L10.5 15.5 L17 8.5",false) }
    val Add: ImageVector by lazy { crear("Add","M12 4 V20 M4 12 H20",false) }
    val Remove: ImageVector by lazy { crear("Remove","M4 12 H20",false) }
    val Delete: ImageVector by lazy { crear("Delete","M3 6 H21 M9 6 V3 H15 V6 M5 6 L6 21 H18 L19 6 M10 10 V17 M14 10 V17",false) }
    val Edit: ImageVector by lazy { crear("Edit","M4 20 L5 14 L16 3 L21 8 L10 19 Z M13 6 L18 11 M4 20 H20",false) }
    val Replay: ImageVector by lazy { crear("Replay","M4 9 A8.5 8.5 0 1 1 4 16 M4 3 V9 H10",false) }
    val Logout: ImageVector by lazy { crear("Logout","M10 3 H4 V21 H10 M9 12 H22 M16 6 L22 12 L16 18",false) }
    val SwapHoriz: ImageVector by lazy { crear("SwapHoriz","M3 8 H20 M15 3 L20 8 L15 13 M21 17 H4 M9 12 L4 17 L9 22",false) }
    val DarkMode: ImageVector by lazy { crear("DarkMode","M20.5 15 A9 9 0 0 1 9 3 A9.5 9.5 0 1 0 20.5 15 Z",false) }
    val AccessTime: ImageVector by lazy { crear("AccessTime","M21 12 A9 9 0 1 1 3 12 A9 9 0 1 1 21 12 M12 6 V12 L16 15",false) }
    val Block: ImageVector by lazy { crear("Block","M21 12 A9 9 0 1 1 3 12 A9 9 0 1 1 21 12 M5.5 5.5 L18.5 18.5",false) }
    val Inventory2: ImageVector by lazy { crear("Inventory2","M3 3 H21 V8 H3 Z M5 8 V21 H19 V8 M9 12 H15",false) }
    val Restaurant: ImageVector by lazy { crear("Restaurant","M4 2 V9 C4 12 10 12 10 9 V2 M7 2 V22 M20 2 C15 4 15 9 15 12 H20 M20 2 V22",false) }
    val Mail: ImageVector by lazy { crear("Mail","M3 5 H21 V19 H3 Z M3 6 L12 13 L21 6",false) }
    val Lock: ImageVector by lazy { crear("Lock","M5 10 H19 V21 H5 Z M8 10 V6 A4 4 0 0 1 16 6 V10 M12 14 V17",false) }
    val Save: ImageVector by lazy { crear("Save","M3 3 H17 L21 7 V21 H3 Z M7 3 V9 H16 V3 M7 21 V14 H17 V21",false) }
    val Payments: ImageVector by lazy { crear("Payments","M2 5 H22 V19 H2 Z M15 12 A3 3 0 1 1 9 12 A3 3 0 1 1 15 12 M5 9 H6 M18 15 H19",false) }
}
