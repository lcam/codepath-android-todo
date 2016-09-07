# Pre-work - Simple ToDo ver 4.0

SimpleToDo is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Leon Cam

Time spent: 32 hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) with SugarORM instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ ] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) for adding items

## Changelog

ver 1.5
* [x] Replaced Listview with RecylerView

ver 2.0
* [x] Replaced Actionbar with Toolbar.  Toolbar reacts to scrolling.
* [x] For adding items, moved text field and button to a dialog fragment, which can be invoked via toolbar button
* [x] Updated color scheme, increased font size of task list
* [x] Keyboard automatically appears to add or edit tasks

ver 3.0
* [x] Persist data with SQLite using ActiveAndroid
* [x] For editing items, moved standalone edit activity to a dialog fragment

ver 4.0
* [x] ActiveAndroid no longer actively support, persist data with SQLite using SugarORM instead
* [x] Implemented completion due dates for each task

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/Fs0zlX6.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Making sense of several new methods such as getStringExtra() and putExtra() was important to completing the 1st version of this assingment.

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
