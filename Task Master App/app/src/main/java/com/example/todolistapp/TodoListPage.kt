package com.example.todolistapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolistapp.ui.theme.OrangeDark
import com.example.todolistapp.ui.theme.OrangeLight
import com.example.todolistapp.ui.theme.Purple80
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListPage(viewModel: TodoViewModel) {
    val navController = rememberNavController()
    val todoList by viewModel.todoList.observeAsState()
    var inputText by remember { mutableStateOf("") }
    var inputDesc by remember { mutableStateOf("") }
    var taskId = 0
    var toUpdate = false
    NavHost(navController = navController, startDestination = Routes.todoListPage, builder = {
        composable(Routes.todoListPage) {
            Scaffold(
                modifier = Modifier.padding(10.dp),
                floatingActionButton = {
                    Column {
                        ExtendedFloatingActionButton(
                            icon = {
                                Icon(
                                    Icons.Rounded.AddCircle,
                                    contentDescription = "Add Task",
                                    tint = Color.White,
                                )
                            },
                            text = {
                                Text(
                                    text = "Add Task",
                                    color = Color.White,
                                )
                            },
                            onClick = {
                                inputText = ""
                                inputDesc = ""
                                navController.navigate(Routes.addTodoPage)
                            },
                            modifier = Modifier.padding(horizontal = 12.dp),
                            containerColor = OrangeDark,
                            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
                        )
                    }
                },
                containerColor = Color.White,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(it)
                ) {
                    if (todoList?.isNotEmpty() == true) {
                        LazyColumn(
                            content = {
                                item {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(shape = RoundedCornerShape(8.dp))
                                            .background(color = Purple80)
                                            .padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp),
                                    ) {
                                        Text(
                                            text = "Welcome User👋,",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                        Text(
                                            text = "Let’s get things done\none task at a time! 🚀 ✅",
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = OrangeDark
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                                itemsIndexed(todoList!!) { index: Int, item: Todo ->
                                    TodoItem(
                                        item = item,
                                        onEdit = {
                                            toUpdate = true
                                            inputText = item.title
                                            inputDesc = item.description
                                            taskId = item.id
                                            navController.navigate(Routes.addTodoPage)
                                        },
                                        onCopy = {
                                            viewModel.copyTodo(item.id)
                                            inputText = item.title
                                            inputDesc = item.description
                                            navController.navigate(Routes.addTodoPage)
                                        },
                                        onDelete = {
                                            viewModel.deleteTodo(item.id)
                                        }
                                    )
                                }
                            }
                        )
                    }
                    if (todoList?.isEmpty() == true) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.empty),
                                contentDescription = "Empty Screen",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            )

                        }
                    }
                }
            }
        }
        composable(Routes.addTodoPage) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
            ) {
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "YOUR TO-DO",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = OrangeDark
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            label = { Text("Task Title") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = OrangeLight,
                                unfocusedBorderColor = OrangeLight,
                                focusedLabelColor = OrangeDark,
                                unfocusedLabelColor = OrangeDark,
                                cursorColor = OrangeDark,
                                focusedTextColor = OrangeDark,
                                unfocusedTextColor = OrangeDark
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                            maxLines = 1
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = inputDesc,
                            onValueChange = { inputDesc = it },
                            label = { Text("Task Description") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = OrangeLight,
                                unfocusedBorderColor = OrangeLight,
                                focusedLabelColor = OrangeDark,
                                unfocusedLabelColor = OrangeDark,
                                cursorColor = OrangeDark,
                                focusedTextColor = OrangeDark,
                                unfocusedTextColor = OrangeDark
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                            minLines = 5,
                            maxLines = 5
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Button(
                                onClick = {
                                    when (toUpdate) {
                                        false -> viewModel.addTodo(inputText, inputDesc)
                                        true -> viewModel.editTodo(taskId, inputText, inputDesc)
                                    }
                                    toUpdate = false
                                    navController.navigate(Routes.todoListPage) {
                                        popUpTo(0)
                                    }
                                },
                                modifier = Modifier.padding(horizontal = 12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = OrangeDark,
                                    contentColor = Color.White,
                                ),
                            ) {
                                Text(
                                    text = "Save Task",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    })


}

@Composable
fun TodoItem(item: Todo, onEdit: () -> Unit, onCopy: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(OrangeLight)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceAround) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.White
                    )
                }
                IconButton(onClick = onCopy) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Copy",
                        tint = Color.White
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(7f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = item.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Start,
                    color = OrangeDark
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.description,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = OrangeDark,
                    modifier = Modifier.defaultMinSize(minHeight = 80.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = SimpleDateFormat(
                        "hh:mm aa, dd/MM",
                        Locale.ENGLISH
                    ).format(item.createdAt),
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    color = OrangeDark
                )
            }
        }
    }
}