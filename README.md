# Fetch Hiring Data Android App

An Android app written in Java that retrieves data from [https://fetch-hiring.s3.amazonaws.com/hiring.json](https://fetch-hiring.s3.amazonaws.com/hiring.json) and displays it to the user with the following features:

- Retrieves and parses data from a specific URL using OkHttpClient.
- Groups the data by "listId."
- Sorts the results first by "listId" and then by "name" when displaying.
- Filters out any items where "name" is blank or null.
- Presents the final result in a cardview
- Follows advanced Object-Oriented Design (OOD) principles like SOLID, including Dependency Inversion (abstracting the item class to an interface).
- Includes robust testing using Android Test and JUnit Test, with thread safety ensured using CountDownLatch.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This Android app is designed to fetch data from a remote JSON source and display it in an organized and user-friendly manner. It follows specific requirements to ensure the data is grouped, sorted, and filtered appropriately. Additionally, it adopts advanced Object-Oriented Design principles, including Dependency Inversion, to maintain code quality and modularity.

## Features

- Retrieves and parses data from a specific URL using OkHttpClient.
- Groups items by "listId."
- Sorts items by "listId" and then by "name."
- Filters out items with blank or null "name" values.
- Presents the final result in a cardview.
- Follows advanced Object-Oriented Design (OOD) principles like SOLID and Dependency Inversion.
- Includes robust testing using Android Test and JUnit Test, with thread safety ensured using CountDownLatch.

## Screenshots

Include screenshots or GIFs showcasing your app's user interface and functionality. Add captions to explain each screenshot.

![Screenshot 1](./screenshots/unfetchedPage.png)
*Intialily, this app does not show any data until user click get data button.*

![Screenshot 2](./screenshots/fetchedPage.png)
*When user click the button, data will show up.*

## Installation

To install and run the app, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/LuyaoWang123/fetchAndroid.git
