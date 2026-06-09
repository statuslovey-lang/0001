package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

enum class Screen {
    Dashboard,
    Directory,
    AboutUs
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                var currentScreen by remember { mutableStateOf(Screen.Dashboard) }
                
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AppBottomNavigation(
                            currentScreen = currentScreen,
                            onScreenSelected = { currentScreen = it }
                        )
                    }
                ) { innerPadding ->
                    when (currentScreen) {
                        Screen.Dashboard -> DashboardScreen(modifier = Modifier.padding(innerPadding))
                        Screen.Directory -> DirectoryScreen(modifier = Modifier.padding(innerPadding))
                        Screen.AboutUs -> AboutUsScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "मालाणी सेवा संस्थान बिठुजा",
                        color = OnSurface,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "BITHUJA, RAJASTHAN",
                        color = OnSurfaceVariant,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium, letterSpacing = 0.5.sp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Surface
            ),
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = OnSurfaceVariant
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(36.dp)
                        .background(PrimaryContainer, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        tint = OnPrimaryContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            item { GreetingCard() }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "कुल सदस्य",
                        value = "2,450",
                        icon = Icons.Default.Groups,
                        color = OnPrimaryContainer,
                        containerColor = PrimaryContainer,
                        subText = "पिछले माह से +12"
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "सक्रिय कोष",
                        value = "₹ 15.2L",
                        icon = Icons.Default.AccountBalance,
                        color = OnSecondaryContainer,
                        containerColor = SecondaryContainer,
                        subText = "अंतिम अपडेट: आज"
                    )
                }
            }

            item {
                Text(
                    text = "त्वरित सेवाएँ",
                    color = Primary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "नियम पुस्तिका",
                        subtitle = "संस्थान के नियम",
                        icon = Icons.Default.MenuBook
                    )
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "कैलकुलेटर",
                        subtitle = "लाभ गणना",
                        icon = Icons.Default.Calculate
                    )
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "पहचान पत्र",
                        subtitle = "डाउनलोड करें",
                        icon = Icons.Default.Badge
                    )
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        title = "बही-खाता",
                        subtitle = "लेन-देन विवरण",
                        icon = Icons.Default.AccountBalance
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun GreetingCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "राम राम सा!",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = OnPrimary
            )
            Text(
                text = "मालाणी सेवा संस्थान में आपका स्वागत है।",
                style = MaterialTheme.typography.bodyMedium,
                color = OnPrimary.copy(alpha = 0.9f),
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x33FFFFFF), RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Campaign,
                    contentDescription = null,
                    tint = OnPrimary,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "आगामी वार्षिक बैठक 15 नवंबर को बिठुजा में आयोजित की जाएगी।",
                    style = MaterialTheme.typography.labelMedium,
                    color = OnPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    containerColor: Color,
    subText: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelSmall,
                        color = color
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = color,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(color.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = subText,
                    style = MaterialTheme.typography.bodySmall,
                    color = color,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ActionCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: ImageVector
) {
    Card(
        modifier = modifier.clickable { },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Outline)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(SurfaceContainerHighest, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = OnSurface,
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = OnSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun AppBottomNavigation(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = SurfaceContainer,
        contentColor = OnSurfaceVariant
    ) {
        NavigationBarItem(
            selected = currentScreen == Screen.Dashboard,
            onClick = { onScreenSelected(Screen.Dashboard) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("होम") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OnPrimaryContainer,
                selectedTextColor = OnSurface,
                indicatorColor = PrimaryContainer,
                unselectedIconColor = OnSurfaceVariant,
                unselectedTextColor = OnSurfaceVariant
            )
        )
        NavigationBarItem(
            selected = currentScreen == Screen.Directory,
            onClick = { onScreenSelected(Screen.Directory) },
            icon = { Icon(Icons.Default.Groups, contentDescription = "Directory") },
            label = { Text("सदस्य") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OnPrimaryContainer,
                selectedTextColor = OnSurface,
                indicatorColor = PrimaryContainer,
                unselectedIconColor = OnSurfaceVariant,
                unselectedTextColor = OnSurfaceVariant
            )
        )
        NavigationBarItem(
            selected = currentScreen == Screen.AboutUs,
            onClick = { onScreenSelected(Screen.AboutUs) },
            icon = { Icon(Icons.Default.Info, contentDescription = "About Us") },
            label = { Text("हमारे बारे में") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = OnPrimaryContainer,
                selectedTextColor = OnSurface,
                indicatorColor = PrimaryContainer,
                unselectedIconColor = OnSurfaceVariant,
                unselectedTextColor = OnSurfaceVariant
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "हमारे बारे में",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = OnSurface
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Background
                )
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(Primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Groups,
                            contentDescription = "Community",
                            tint = OnPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "मालाणी सेवा संस्थान",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnPrimaryContainer,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Text(
                        text = "बिठुजा, राजस्थान",
                        style = MaterialTheme.typography.titleMedium,
                        color = OnPrimaryContainer.copy(alpha = 0.8f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        item {
            SectionHeader("संस्था का इतिहास")
            Text(
                text = "मालाणी सेवा संस्थान की स्थापना वर्ष 1984 में राजपूताना की पवित्र भूमि बिठुजा में की गई थी। हमारे समुदाय के बुज़ुर्गों ने एक संयुक्त, मज़बूत और एकजूट समाज के निर्माण का जो सपना देखा था, उसे साकार करने के लिए इस संस्था का निर्माण किया गया। शुरुआत में यह केवल एक छोटे से समूह के रूप में शुरू हुआ, जो आज एक विशाल वट-वृक्ष का रूप ले चुका है।",
                style = MaterialTheme.typography.bodyLarge,
                color = OnSurfaceVariant,
                lineHeight = 24.sp
            )
        }

        item {
            SectionHeader("हमारा मुख्य उद्देश्य")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ObjectiveRow(Icons.Default.MenuBook, "शिक्षा सहायता", "समाज के सभी वर्गों तक उच्च शिक्षा पहुँचाना।")
                    ObjectiveRow(Icons.Default.Calculate, "चिकित्सा अनुदान", "गंभीर बीमारियों के इलाज के लिए आर्थिक मदद।")
                    ObjectiveRow(Icons.Default.Campaign, "विवाह योजनाएं", "ज़रूरतमंद परिवारों को विवाह में सहायता (मायरा एवं पुत्र/पुत्री विवाह योगदान)।")
                }
            }
        }

        item {
            SectionHeader("दीर्घकालिक दृष्टिकोण")
            Text(
                text = "हम एक ऐसे आत्मनिर्भर और शिक्षित समुदाय के निर्माण का विज़न रखते हैं, जहाँ हमारी प्राचीन राजस्थानी विरासत और आधुनिक अवसरों का बेहतरीन संगम हो। हमारा संकल्प है कि मालानी समुदाय का कोई भी परिवार कमज़ोर न पड़े और हर व्यक्ति समाज के विकास में अपनी भागीदारी निभाए।",
                style = MaterialTheme.typography.bodyLarge,
                color = OnSurfaceVariant,
                lineHeight = 24.sp
            )
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

data class Member(
    val id: String,
    val name: String,
    val role: String,
    val location: String,
    val phone: String
)

val sampleMembers = listOf(
    Member("S101", "केवलराम माली", "संस्थापक", "बिठुजा", "9001611645"),
    Member("S102", "घेवरचंद प्रजापत", "संस्थापक", "बिठुजा", "6375327642"),
    Member("S103", "सुजाराम माली", "संस्थापक / एजेंट", "जसोल", "8005995812"),
    Member("S104", "अर्जुन प्रजापत", "संस्थापक", "बिठुजा", "9024760816"),
    Member("S105", "गौतम चन्द", "स्वयंसेवक", "जसोल", "9057591906"),
    Member("S106", "सांवल राम जी", "दानदाता", "पारलू", "8789456515"),
    Member("S107", "गजरो", "सदस्य", "पारलू", "9057591906")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectoryScreen(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    
    val filteredMembers = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            sampleMembers
        } else {
            sampleMembers.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.location.contains(searchQuery, ignoreCase = true) ||
                it.role.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/csv")
    ) { uri ->
        uri?.let {
            try {
                context.contentResolver.openOutputStream(it)?.use { outputStream ->
                    val csvHeader = "ID,Name,Role,Location,Phone\n"
                    val csvData = filteredMembers.joinToString(separator = "\n") { member ->
                        "${member.id},${member.name},${member.role},${member.location},${member.phone}"
                    }
                    outputStream.write((csvHeader + csvData).toByteArray())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "सदस्य निर्देशिका",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
            },
            actions = {
                IconButton(onClick = { launcher.launch("members.csv") }) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Export CSV",
                        tint = Primary
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Background
            )
        )
        
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("नाम, पद या स्थान से खोजें...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Surface,
                unfocusedContainerColor = Surface,
                focusedBorderColor = Primary,
                unfocusedBorderColor = OutlineVariant
            ),
            singleLine = true
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }
            
            items(filteredMembers.size) { index ->
                MemberCard(member = filteredMembers[index])
            }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun MemberCard(member: Member) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(PrimaryContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = member.name.take(1),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = OnPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = member.name,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = OnSurface
                        )
                        
                        val roleColors = when {
                            member.role.contains("संस्थापक") -> Pair(TertiaryContainer, OnTertiaryContainer)
                            member.role.contains("दानदाता") -> Pair(SecondaryContainer, OnSecondaryContainer)
                            member.role.contains("स्वयंसेवक") -> Pair(PrimaryContainer, OnPrimaryContainer)
                            else -> Pair(SurfaceContainerHigh, OnSurfaceVariant)
                        }

                        Text(
                            text = member.role,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = roleColors.second,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .background(roleColors.first, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Text(
                    text = "#${member.id}",
                    style = MaterialTheme.typography.labelSmall,
                    color = OnSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = member.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone",
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = member.phone,
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        color = Primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun ObjectiveRow(icon: ImageVector, title: String, description: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(SurfaceContainerHigh, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = OnSurface
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurfaceVariant
            )
        }
    }
}
