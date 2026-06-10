package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
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
    Login,
    VerifyOtp,
    Dashboard,
    Directory,
    Gallery,
    AboutUs,
    AddMember,
    Register,
    IdCard
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val membersList = remember { mutableStateListOf(*sampleMembers.toTypedArray()) }
                var currentScreen by remember { mutableStateOf(Screen.Login) }
                
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentScreen in listOf(Screen.Dashboard, Screen.Directory, Screen.Gallery, Screen.AboutUs)) {
                            AppBottomNavigation(
                                currentScreen = currentScreen,
                                onScreenSelected = { currentScreen = it }
                            )
                        }
                    },
                    floatingActionButton = {
                        if (currentScreen == Screen.Directory) {
                            FloatingActionButton(
                                onClick = { currentScreen = Screen.AddMember },
                                containerColor = Primary,
                                contentColor = OnPrimary
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add Member")
                            }
                        }
                    }
                ) { innerPadding ->
                    when (currentScreen) {
                        Screen.Login -> LoginScreen(
                            onLoginSuccess = { currentScreen = Screen.VerifyOtp },
                            onNavigateToRegister = { currentScreen = Screen.Register },
                            modifier = Modifier.padding(innerPadding)
                        )
                        Screen.VerifyOtp -> VerifyOtpScreen(
                            onVerifySuccess = { currentScreen = Screen.Dashboard },
                            modifier = Modifier.padding(innerPadding)
                        )
                        Screen.Dashboard -> DashboardScreen(
                            onNavigateToIdCard = { currentScreen = Screen.IdCard },
                            onNavigateToRegister = { currentScreen = Screen.Register },
                            modifier = Modifier.padding(innerPadding)
                        )
                        Screen.Directory -> DirectoryScreen(membersList = membersList, modifier = Modifier.padding(innerPadding))
                        Screen.Gallery -> GalleryScreen(modifier = Modifier.padding(innerPadding))
                        Screen.AboutUs -> AboutUsScreen(modifier = Modifier.padding(innerPadding))
                        Screen.AddMember -> AddMemberScreen(
                            onMemberAdded = { membersList.add(it) },
                            onNavigateBack = { currentScreen = Screen.Directory },
                            modifier = Modifier.padding(innerPadding)
                        )
                        Screen.Register -> AddMemberScreen(
                            onMemberAdded = { membersList.add(it) },
                            onNavigateBack = { currentScreen = Screen.Login },
                            modifier = Modifier.padding(innerPadding)
                        )
                        Screen.IdCard -> IdCardScreen(
                            onNavigateBack = { currentScreen = Screen.Dashboard },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onNavigateToIdCard: () -> Unit, onNavigateToRegister: () -> Unit, modifier: Modifier = Modifier) {
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

            item { GreetingCard(onNavigateToRegister = onNavigateToRegister) }

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
                        icon = Icons.Default.Badge,
                        onClick = onNavigateToIdCard
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
fun GreetingCard(onNavigateToRegister: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "राम राम सा!",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = OnPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "मालाणी सेवा संस्थान में आपका स्वागत है।",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = OnPrimary
            )
            Text(
                text = "Welcome to Malani Seva Sansthan.",
                style = MaterialTheme.typography.titleSmall,
                color = OnPrimary.copy(alpha = 0.9f)
            )
            
            Text(
                text = "Together, we build a united, strong, and empowered community. Join us in our social initiatives and welfare programs.",
                style = MaterialTheme.typography.bodyMedium,
                color = OnPrimary.copy(alpha = 0.9f),
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
            )

            Button(
                onClick = onNavigateToRegister,
                colors = ButtonDefaults.buttonColors(
                    containerColor = OnPrimary,
                    contentColor = Primary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Groups,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Become a Member / सदस्य बनें",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
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
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.clickable { onClick() },
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
            selected = currentScreen == Screen.Gallery,
            onClick = { onScreenSelected(Screen.Gallery) },
            icon = { Icon(Icons.Default.PhotoLibrary, contentDescription = "Gallery") },
            label = { Text("गैलरी") },
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
                    ObjectiveRow(Icons.Default.Campaign, "पुत्र/पुत्री विवाह योगदान", "ज़रूरतमंद परिवारों को विवाह में सहायता।")
                    ObjectiveRow(Icons.Default.CardGiftcard, "मायरा योजना", "ज़रूरतमंद परिवारों को मायरा में आर्थिक सहायता।")
                    ObjectiveRow(Icons.Default.VolunteerActivism, "सहायता योजना", "आसहाय / दुर्घटना पर आर्थिक सहायता।")
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
fun DirectoryScreen(membersList: List<Member>, modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    
    val filteredMembers = remember(searchQuery, membersList) {
        if (searchQuery.isBlank()) {
            membersList
        } else {
            membersList.filter {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemberScreen(
    onMemberAdded: (Member) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { androidx.compose.runtime.mutableIntStateOf(1) }
    
    // Step 1: Personal Profile
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var bloodGroup by remember { mutableStateOf("") }

    // Step 2: Family Details
    var fatherName by remember { mutableStateOf("") }
    var nomineeName by remember { mutableStateOf("") }
    var nomineeRelation by remember { mutableStateOf("") }
    var mayaraSchemeOptIn by remember { mutableStateOf(false) }
    
    // Step 3: Document Uploads
    var aadharNumber by remember { mutableStateOf("") }
    var isPhotoUploaded by remember { mutableStateOf(false) }
    var isAadharUploaded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        TopAppBar(
            title = {
                Text(
                    "नया सदस्य / New Member",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = OnSurface)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
        )
        
        // Progress Indicator
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Personal", "Family", "Docs", "Payment").forEachIndexed { index, title ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = if (step > index + 1) Primary else if (step == index + 1) PrimaryContainer else SurfaceVariant,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (index + 1).toString(),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (step > index + 1) OnPrimary else if (step == index + 1) OnPrimaryContainer else OnSurfaceVariant
                        )
                    }
                    Text(title, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 4.dp))
                }
                if (index < 3) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                        color = if (step > index + 1) Primary else SurfaceVariant
                    )
                }
            }
        }
        
        HorizontalDivider()

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (step) {
                1 -> {
                    Text("व्यक्तिगत जानकारी (Personal Profile)", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = Primary)
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("पूरा नाम / Full Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    OutlinedTextField(value = dob, onValueChange = { dob = it }, label = { Text("जन्मतिथि / Date of Birth") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("मोबाइल नंबर / Mobile Number") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("निवास स्थान / Address") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    OutlinedTextField(value = bloodGroup, onValueChange = { bloodGroup = it }, label = { Text("रक्त समूह / Blood Group") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                }
                2 -> {
                    Text("पारिवारिक जानकारी (Family Details)", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = Primary)
                    OutlinedTextField(value = fatherName, onValueChange = { fatherName = it }, label = { Text("पिता का नाम / Father's Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    OutlinedTextField(value = nomineeName, onValueChange = { nomineeName = it }, label = { Text("नामांकित व्यक्ति / Nominee Name") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    OutlinedTextField(value = nomineeRelation, onValueChange = { nomineeRelation = it }, label = { Text("नामांकित से संबंध / Nominee Relation") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = PrimaryContainer.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("मायरा योजना में शमिल हों?", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                                Text("Opt-in for Mayara Scheme", style = MaterialTheme.typography.labelSmall)
                            }
                            Switch(
                                checked = mayaraSchemeOptIn,
                                onCheckedChange = { mayaraSchemeOptIn = it }
                            )
                        }
                    }
                }
                3 -> {
                    Text("दस्तावेज़ अपलोड (Document Upload)", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = Primary)
                    OutlinedTextField(value = aadharNumber, onValueChange = { aadharNumber = it }, label = { Text("आधार नंबर / Aadhar Number") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { isAadharUploaded = true },
                        colors = CardDefaults.cardColors(containerColor = if (isAadharUploaded) Color(0xFFE8F5E9) else SurfaceVariant),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, if (isAadharUploaded) Color(0xFF4CAF50) else Outline)
                    ) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Badge, contentDescription = null, tint = if (isAadharUploaded) Color(0xFF4CAF50) else OnSurfaceVariant)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(if (isAadharUploaded) "Aadhar Uploaded" else "Upload Aadhar Card", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { isPhotoUploaded = true },
                        colors = CardDefaults.cardColors(containerColor = if (isPhotoUploaded) Color(0xFFE8F5E9) else SurfaceVariant),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, if (isPhotoUploaded) Color(0xFF4CAF50) else Outline)
                    ) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccountCircle, contentDescription = null, tint = if (isPhotoUploaded) Color(0xFF4CAF50) else OnSurfaceVariant)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(if (isPhotoUploaded) "Photo Uploaded" else "Upload Passport Size Photo", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
                4 -> {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.size(80.dp).background(Color(0xFFE8F5E9), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(40.dp))
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            "Submission Successful",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF4CAF50)
                        )
                        Text(
                            "Your application has been received.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = PrimaryContainer),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Payment Gateway Integration", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                                Text("Total Amount: ₹200", style = MaterialTheme.typography.bodyLarge, color = Primary, modifier = Modifier.padding(vertical = 8.dp))
                                Button(
                                    onClick = { /* Simulated File Download */ },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                                ) {
                                    Icon(Icons.Default.Download, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Download Receipt")
                                }
                            }
                        }
                    }
                }
            }
        }
        
        HorizontalDivider()
        
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (step > 1 && step < 4) {
                OutlinedButton(onClick = { step-- }) { Text("Back") }
            } else {
                Spacer(modifier = Modifier.width(8.dp))
            }
            
            if (step < 3) {
                Button(onClick = { step++ }) { Text("Next") }
            } else if (step == 3) {
                Button(onClick = { 
                    step = 4 
                    if (name.isNotBlank()) {
                        val newId = "S${(100..999).random()}"
                        onMemberAdded(Member(newId, name, "Primary Member", location, phone))
                    }
                }) { Text("Submit / Pay") }
            } else {
                Button(onClick = onNavigateBack, modifier = Modifier.fillMaxWidth()) { Text("Back to Home") }
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit, modifier: Modifier = Modifier) {
    var mobile by remember { mutableStateOf("") }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Groups,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "मालाणी सेवा संस्थान",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = OnSurface
        )
        Text(
            text = "लॉगिन करें",
            style = MaterialTheme.typography.titleMedium,
            color = OnSurfaceVariant,
            modifier = Modifier.padding(bottom = 32.dp, top = 8.dp)
        )

        OutlinedTextField(
            value = mobile,
            onValueChange = { mobile = it },
            label = { Text("मोबाइल नंबर") },
            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = OutlineVariant
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { if (mobile.isNotBlank()) onLoginSuccess() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = OnPrimary),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text("ओटीपी भेजें", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(onClick = onNavigateToRegister) {
            Text("नया सदस्य पंजीकरण", color = Primary, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun VerifyOtpScreen(onVerifySuccess: () -> Unit, modifier: Modifier = Modifier) {
    var otp by remember { mutableStateOf("") }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ओटीपी दर्ज करें",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = OnSurface
        )
        Text(
            text = "आपके मोबाइल नंबर पर भेजा गया 4 अंकों का ओटीपी दर्ज करें",
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp, top = 8.dp)
        )

        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text("OTP") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = OutlineVariant
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { if (otp.isNotBlank()) onVerifySuccess() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = OnPrimary),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text("सत्यापित करें", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdCardScreen(onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        TopAppBar(
            title = {
                Text(
                    "पहचान पत्र",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = OnSurface)
                }
            },
            actions = {
                IconButton(onClick = { 
                    // Simulate Download PDF
                    val intent = android.content.Intent(android.content.Intent.ACTION_CREATE_DOCUMENT).apply {
                        addCategory(android.content.Intent.CATEGORY_OPENABLE)
                        type = "application/pdf"
                        putExtra(android.content.Intent.EXTRA_TITLE, "id_card.pdf")
                    }
                    context.startActivity(intent)
                }) {
                    Icon(Icons.Default.Download, contentDescription = "Download PDF", tint = Primary)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.63f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                border = BorderStroke(2.dp, Color(0xFFFFD700))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.5f)
                            .background(Color(0xFF0D47A1)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "मालाणी सेवा संस्थान",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFFFFD700)
                            )
                            Text(
                                text = "MALANI SEVA SANSTHAN",
                                style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 1.sp),
                                color = Color.White
                            )
                            Text(
                                text = "बिठुजा, राजस्थान",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                    
                    // Profile Info
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4.5f)
                            .padding(16.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            // Photo
                            Box(
                                modifier = Modifier
                                    .size(width = 80.dp, height = 100.dp)
                                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                                    .border(1.dp, Color(0xFF0D47A1), RoundedCornerShape(4.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Photo",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                            
                            Spacer(modifier = Modifier.width(16.dp))
                            
                            // Info & QR
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "केवलराम माली",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF0D47A1)
                                )
                                Text(
                                    text = "संस्थापक",
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFFD4AF37)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Icon(
                                    imageVector = Icons.Default.QrCode2,
                                    contentDescription = "QR Code",
                                    tint = Color.Black,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = Color.LightGray)
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("सदस्य क्र. (ID)", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                Text("S101", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Color.Black)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("निवासी (Address)", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                Text("माजीवाला", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Color.Black)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("जन्म (DOB)", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                Text("20-Sep-2011", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Color.Black)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("जाति (Caste)", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                Text("माली", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Color.Black)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("रक्त समूह (Blood Group)", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                Text("O+", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Color.Black)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("मो. (Mobile)", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                Text("9001611645", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Color.Black)
                            }
                        }
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                            Column {
                                Text("आपात्कालीन सं. (Emergency)", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                Text("9252323252", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Color.Black)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Devendra", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF0D47A1), modifier = Modifier.padding(bottom = 2.dp))
                                HorizontalDivider(color = Color.Black, modifier = Modifier.width(60.dp))
                                Text("Auth. Sign", style = MaterialTheme.typography.labelSmall, color = Color.Gray, modifier = Modifier.padding(top = 2.dp))
                            }
                        }
                    }
                    
                    // Footer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.4f)
                            .background(Color(0xFF0D47A1)),
                        contentAlignment = Alignment.Center
                    ) {
                         Text(
                            text = "www.malaniseva.org",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

data class GalleryImage(val id: String, val title: String)

val dummyImages = listOf(
    GalleryImage("1", "रक्तदान शिविर - 2023"),
    GalleryImage("2", "वार्षिक सम्मेलन - 2023"),
    GalleryImage("3", "पौधारोपण अभियान"),
    GalleryImage("4", "शिक्षा जागरूकता रैली"),
    GalleryImage("5", "स्वच्छता अभियान"),
    GalleryImage("6", "चिकित्सा शिविर - जसोल")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().background(Background)) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "फोटो गैलरी",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Background)
        )
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp, top = 8.dp)
        ) {
            items(dummyImages.size) { index ->
                val image = dummyImages[index]
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier.aspectRatio(1f)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(PrimaryContainer.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.6f))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = image.title,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }
    }
}