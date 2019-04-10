# Test Project - Car

## Features
- Load data and show from API.
- Save offline data in background service when ever fetch new data.
- Refresh button.
- Show alert and text warning when reach error.
- Support migration.

## Architecture
#### MVVM Design Pattern
#### Base Classes
* `BaseActivity.kt`
* `BaseViewModel.kt`
* `BaseRecyclerView.kt`

Created with purpose to remove redundant code and support common functions.

#### Code rules

- Variable with prefix bind understand that will be directly connect to view component and interact with it.

- Prefix '_' in function name to determine that is private function.

- Order functions in any class must be placed near together base on function type (abstract, private, open, etc.) to easily control.

- Naming table in database must be add 'tbl' prefix, also with column name but following Pascal Case convention.

- Binding variable from view model to view must be consistent in one way. Example MainActivity binding logic placed at MainActiviyt.kt, ArticleCellViewModel binding logic placed at BindingAdapter.kt or bind directly to xml file.

#### Database

- Table Article

| IdArticle      | Title  | DateTime  | Image  | Ingress  | Created  | Changed
| -------------  | ------ |
| Primary Key    | 

- Table Content

| IdContent*    | IdArticle   | Type  | Subject  | Description  | 
| ------------- | ----------- |
| Primary Key   | Foreign Key |

#### Library Version

All library version number write down in build.gradle ( Project )

## Libraries
- RxJava 2
- Android X
- Dagger 2
- Retrofit 2
- Room Persistence Library
- Glide