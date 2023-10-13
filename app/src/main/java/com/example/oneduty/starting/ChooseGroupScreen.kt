package com.example.oneduty

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.oneduty.sign_in.UserData
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.CompositionLocalProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Context
import android.util.Log

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavHostController
import com.example.oneduty.profile.NurseRole

@Composable
fun ChooseGroupScreen(
    userData: UserData?,
    navController: NavHostController,

) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        fun onClickNext(context: Context) {
            if (userData != null) {
                addGroupToFirebase(group = "ddddd", uid = userData.userId, context = context)
            }
            navController.navigate("temptemp")
        }

        val groupCode = remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .selectableGroup()
        ) {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                Text(text = "그룹 코드를 입력해 주세요!", fontSize = 30.sp)
                TextField(
                    value = groupCode.value,

                    onValueChange = { groupCode.value = it },


                    placeholder = { Text(text = "그룹 코드 입력하기") },

                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),

                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),

                    singleLine = true,
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        if (userData != null) {
                            Log.d("MyTag",userData.role)
                            Log.d("MyTag",NurseRole.HEADNURSE.toString())
                            Log.d("MyTag",
                                (userData.role == NurseRole.HEADNURSE.toString()).toString()
                            )
                        }else{
                            Log.d("MyTag","null!")
                            Log.d("MyTag","null!")
                            Log.d("MyTag","null!")
                            Log.d("MyTag","null!")
                            Log.d("MyTag","null!")
                            Log.d("MyTag","null!")

                        }

//                        onClickNext(
//                            context = context
//                        )
                    }) {
                        Text(text = "선택", fontSize = 30.sp)
                    }
                }

                if (userData != null) {

                    if(userData.role == NurseRole.HEADNURSE.toString()){
                        CreateGroupButton(
                            userData = userData,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CreateGroupButton(userData: UserData?, navController: NavHostController) {
    val context = LocalContext.current

    fun onClickCreate(context: Context) {
        if (userData != null) {
            addGroupToFirebase(group = "ddddd", uid = userData.userId, context = context)
        }
        navController.navigate("temptemp")
    }
    Text(text = "생성된 그룹이 아직 없으시다면?", fontSize = 15.sp)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            onClickCreate(
                context = context
            )
        }) {
            Text(text = "그룹 생성하기", fontSize = 25.sp)
        }
    }
}

fun addGroupToFirebase(
    group: String, uid: String, context: Context
) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbUser = db.collection("users").document(uid)
    dbUser.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                // Get the current value of the field
                val currentValue = document.get("group")

                if (currentValue != null) {
                    val updatedField = mapOf("group" to group)
                    dbUser.update(updatedField)
                        .addOnSuccessListener {
                            Toast.makeText(
                                context, "아이디가 성공적으로 생성되었습니다!", Toast.LENGTH_SHORT
                            ).show()
                        }.addOnFailureListener { e ->
                            Toast.makeText(context, "아이디 생성에 실패했습니다\n$e", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
}