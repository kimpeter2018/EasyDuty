package com.example.oneduty

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneduty.sign_in.UserData
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import com.example.oneduty.profile.NurseRole
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Context

import android.widget.Toast

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController


@Composable
fun ChooseRoleScreen(
    userData: UserData?,
    navController: NavHostController,

) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomRadioButtons(
            userData = userData,
            context = LocalContext.current,
            navController = navController
        )
    }
}

@Composable
private fun CustomRadioButtons(
    userData: UserData?,
    context: Context,
    navController: NavHostController,
) {

    fun onClickNext(role: String, context: Context) {
        userData?.name?.let {
            addDataToFirebase(
                name = it,
                role = role,
                uid = userData.userId,
                context = context
            )
        }
        navController.navigate("choose_group")
    }

    val nurseList = returnNurseList()

    var selectedItem by remember {
        mutableStateOf(nurseList[0])
    }

    Column(
        modifier = Modifier
            .selectableGroup()
    ) {

        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            Text(text = "당신의 직책이 무엇인가요?", fontSize = 30.sp)
            Box(
                modifier = Modifier
                    .selectable(
                        selected = (selectedItem == NurseRole.HEADNURSE),
                        onClick = { selectedItem = NurseRole.HEADNURSE },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 8.dp)
            ) {
                RadioButtonStyle(
                    selectedItem = selectedItem,
                    thisNurse = NurseRole.HEADNURSE,
                    image = R.drawable.nurse,
                    nurseRoleText = "수간호사"
                )
            }
            Box(
                modifier = Modifier
                    .selectable(
                        selected = (selectedItem == NurseRole.NURSE),
                        onClick = { selectedItem = NurseRole.NURSE },
                        role = Role.RadioButton
                    )
                    .padding(vertical = 8.dp)
            ) {
                RadioButtonStyle(
                    selectedItem = selectedItem,
                    thisNurse = NurseRole.NURSE,
                    image = R.drawable.nurse,
                    nurseRoleText = "간호사"
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    onClickNext(
                        role = selectedItem.toString(),
                        context = context
                    )
                }) {
                    Text(text = "선택", fontSize = 30.sp)
                }
            }

        }
    }
}

@Composable
private fun RadioButtonStyle(
    selectedItem: NurseRole,
    thisNurse: NurseRole,
    image: Int,
    nurseRoleText: String
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .wrapContentSize()
            .border(
                width = 1.dp,
                color =
                if (selectedItem == thisNurse)
                    MaterialTheme.colorScheme.primary
                else
                    Color.LightGray,
                shape = RoundedCornerShape(percent = 10)
            )
            .padding(horizontal = 40.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.Center)
        ) {
            Image(
                modifier = Modifier
                    .size(size = 94.dp)
                    .clip(shape = CircleShape),
                painter = painterResource(id = image),
                contentDescription = "Nurse Image"
            )
            Text(
                text = nurseRoleText,
                fontSize = 30.sp,
            )

        }
    }
}


private fun returnNurseList(): ArrayList<NurseRole> {
    val nurselist = arrayListOf<NurseRole>()
    nurselist.add(
        NurseRole.HEADNURSE
    )
    nurselist.add(
        NurseRole.NURSE
    )

    return nurselist
}

object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}

fun addDataToFirebase(
    name: String, role: String, uid: String, context: Context
) {
    // on below line creating an instance of firebase firestore.
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // creating a collection reference for our Firebase Firestore database.
    val dbUsers: CollectionReference = db.collection("users")

    // adding our data to our courses object class.
    val user = UserData(name = name, role = role, userId = uid)

    // below method is use to add data to Firebase Firestore
    // after the data addition is successful
    dbUsers.document(uid).set(user).addOnSuccessListener {
        // we are displaying a success toast message.
        Toast.makeText(
            context, "아이디가 성공적으로 생성되었습니다!", Toast.LENGTH_SHORT
        ).show()

    }.addOnFailureListener { e ->
        // this method is called when the data addition process is failed.
        // displaying a toast message when data addition is failed.
        Toast.makeText(context, "아이디 생성에 실패했습니다\n$e", Toast.LENGTH_SHORT).show()
    }
}