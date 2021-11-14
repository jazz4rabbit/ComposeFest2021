/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelabs.state.todo

import com.codelabs.state.util.generateRandomTodoItem
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TodoViewModelTest {

    @Test
    fun whenAddItem_updatesList() {
        // given
        val viewModel = TodoViewModel()
        val item = generateRandomTodoItem()

        // when
        viewModel.addItem(item)

        // then
        assertThat(viewModel.todoItems).isEqualTo(listOf(item))
    }

    @Test
    fun whenRemovingItem_updatesList() {
        // given
        val viewModel = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        viewModel.addItem(item1)
        viewModel.addItem(item2)

        // when
        viewModel.removeItem(item1)

        // then
        assertThat(viewModel.todoItems).isEqualTo(listOf(item2))
    }

    @Test
    fun whenEditing_currentEditItemIsCorrectItem() {
        // given
        val viewModel = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        viewModel.addItem(item1)
        viewModel.addItem(item2)

        // when
        viewModel.onEditItemSelected(item1)

        // then
        assertThat(viewModel.currentEditItem).isEqualTo(item1)
    }

    @Test
    fun whenEditingDone_currentEditItemIsCorrectItem() {
        // given
        val viewModel = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        viewModel.addItem(item1)
        viewModel.addItem(item2)

        // when
        viewModel.onEditItemSelected(item1)
        viewModel.onEditDone()

        // then
        assertThat(viewModel.currentEditItem).isNull()
    }

    @Test
    fun whenEditingItem_updatesAreShownInItemAndList() {
        // given
        val viewModel = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        viewModel.addItem(item1)
        viewModel.addItem(item2)

        // when
        viewModel.onEditItemSelected(item1)
        val expected = item1.copy(task = "Update for test case")
        viewModel.onEditItemChange(expected)

        // then
        assertThat(viewModel.todoItems).isEqualTo(listOf(expected, item2))
        assertThat(viewModel.currentEditItem).isEqualTo(expected)
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenEditing_wrongItemThrows() {
        // given
        val viewModel = TodoViewModel()
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        viewModel.addItem(item1)
        viewModel.addItem(item2)

        // when
        viewModel.onEditItemSelected(item1)
        val expected = item2.copy(task = "Update for test case")
        viewModel.onEditItemChange(expected)
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenNotEditing_onEditItemChangeThrows() {
        // given
        val viewModel = TodoViewModel()
        val item = generateRandomTodoItem()

        // when
        viewModel.onEditItemChange(item)
    }
}
