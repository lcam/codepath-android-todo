# Simple ToDo ver 1.6

SimpleToDo is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Developed by: Leon Cam

## Features

* [x] Add new tasks with support for recording due dates and setting priorities levels
* [x] Edit each task's name, priority, and due date
* [x] Drag to reorder tasks
* [x] Swipe to delete tasks
* [x] Todo list is preserved into SQLite
* [x] Inspired by Google Material Design

## Libraries and Patterns Used

1. [SugarORM](http://satyan.github.io/sugar/) - SQLite wrapper
2. [Butterknife](http://jakewharton.github.io/butterknife/) - Bind Android views and callbacks to fields and methods

## Changelog

ver 1.0
* [x] Original release

ver 1.1
* [x] Replaced Listview with RecylerView

ver 1.2
* [x] Replaced Actionbar with Toolbar.  Toolbar reacts to scrolling.
* [x] For adding items, moved text field and button to a dialog fragment, which can be invoked via toolbar button
* [x] Updated color scheme, increased font size of task list
* [x] Keyboard automatically appears to add or edit tasks

ver 1.3
* [x] Persist data with SQLite using ActiveAndroid
* [x] For editing items, moved standalone edit activity to a dialog fragment

ver 1.4
* [x] ActiveAndroid no longer maintained by developer, so SugarORM is used instead
* [x] Implemented completion due dates for each task

ver 1.5
* [x] Tasks can now be set as either low or high priority
* [x] Clicking around calendar and priority icons area no longer triggers edit task dialog
* [x] Updated launcher icon

ver 1.6
* [x] Delete tasks by swiping instead of long clicking
* [x] Reorder tasks by dragging them up or down
* [x] Integrated Butterknife into project to reduce boilerplate code

## Video Walkthrough 

<img src='https://i.imgur.com/TqxZcC8.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## License

    Copyright 2016 Leon Cam

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
