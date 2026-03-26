---
layout: page
title: User Guide
---

# ClientEase User Guide

**ClientEase is a lightweight customer contact manager designed for tech-savvy home-based online business owners** who manage a small to medium customer base,
perform frequent daily updates to customer contact information, and prefer fast, command-line-style text input over
GUI-driven interactions.

Instead of clicking through multiple menus, ClientEase lets you type short commands to add customers, search by fields,
track products and deadlines, and retrieve information instantly, keeping your records organised without the overhead
of full-scale business management systems.

- If you are new to ClientEase, start with the [Quick Start](#quick-start) section.
- If you are looking for a specific command, jump to the [Command Summary](#command-summary).
- If you are a developer, refer to the [Developer Guide](https://ay2526s2-cs2103t-t12-2.github.io/tp/DeveloperGuide.html).

---

## Table of Contents

- [Who Is This Guide For?](#who-is-this-guide-for)
- [Quick Start](#quick-start)
    - [Installation](#installation)
    - [Overview of the Interface](#overview-of-the-interface)
    - [Your First Commands](#your-first-commands)
- [Features](#features)
    - [Notes on Command Format](#notes-on-command-format)
    - [Viewing Help : `help`](#viewing-help--help)
    - [Adding a Customer : `add`](#adding-a-customer--add)
    - [Listing All Customers : `list`](#listing-all-customers--list)
    - [Editing a Customer : `edit`](#editing-a-customer--edit)
    - [Locating Customers : `find`](#locating-customers--find)
    - [Deleting a Customer : `delete`](#deleting-a-customer--delete)
    - [Clearing All Customers : `clear`](#clearing-all-customers--clear)
    - [Exiting the App : `exit`](#exiting-the-app--exit)
- [Saving the Data](#saving-the-data)
- [Editing the Data File](#editing-the-data-file)
- [FAQ](#faq)
- [Known Issues](#known-issues)
- [Command Summary](#command-summary)
- [Glossary](#glossary)

---

## Who Is This Guide For?

ClientEase is built for **home-based online business owners** who:

- Manage a **small to medium customer base** (typically under a few hundred contacts)
- Perform **frequent daily updates** to customer information, such as new orders, contact changes, or delivery deadlines
- Prefer **keyboard-driven workflows** over clicking through menus

This guide assumes you are comfortable with:

| Assumed Skill | What It Means in Practice |
|---|---|
| Basic terminal use | Opening a terminal, using `cd` to navigate folders, running `java -jar` commands |
| Simple command syntax | Typing structured commands like `add name/John Doe contact/98765432` |
| Reading on-screen feedback | Interpreting short success or error messages shown in the app |
| Basic data awareness | Understanding that your data is stored in a local file, and knowing to back it up |

> **Not sure if ClientEase is right for you?** If you manage more than a few hundred customers with complex team
> workflows, you may want a full-scale CRM instead.

---

## Quick Start

### Installation

1. Ensure you have **Java 17 or above** installed. Verify by running:
   ```
   java -version
   ```
   > **Mac users:** Ensure you have the precise JDK version prescribed
   > [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest **`clientease.jar`** file from the
   [releases page here](https://github.com/AY2526S2-CS2103T-T12-2/tp/releases).

3. Move the file to a folder you want to use as your **home folder** (e.g., `~/ClientEase/`).

4. Open a terminal and navigate to that folder:
   ```
   cd ~/ClientEase
   ```

5. Run the application:
   ```
   java -jar clientease.jar
   ```

   ClientEase will launch with a set of sample customer data so you can explore right away.

---

### Overview of the Interface

![Ui](images/Ui.png)

| Area | Purpose |
|---|---|
| **Command Box** (top) | Where you type your commands. |
| **Result Display** (below command box) | Shows success messages or error feedback after each command. |
| **Customer List Panel** | Displays all customers. Each card is color-coded by **Priority Level** (Green/Yellow/Red) based on total product quantity. |
| **Priority Badge** | A small tag (LOW, MEDIUM, HIGH) next to the name for quick status identification. |
| **Status Bar** (bottom) | Shows the data file save location. |

---

### Your First Commands

Here is a short walkthrough to get familiar with ClientEase. Try each command by typing it into the command box and
pressing **Enter**.

**Step 1 - See what's already in the app:**
```
list
```
Expected output: All sample customers are shown in the Customer List Panel.

**Step 2 - Add your first real customer:**
```
add name/Jane Tan contact/91234567;jane@mybusiness.com products/Chocolate Cake:2, Muffin:5 location/Tampines deadline/2025-12-31
```
Expected output: `Added Customer: Jane Tan`

**Step 3 - Find a customer by name:**
```
find Jane
```
Expected output: Only customers whose name matches "Jane" are shown.

**Step 4 - Update a customer's contact details:**
```
edit 1 contact/99887766
```
Expected output: The first customer's contact details are updated.

**Step 5 - Delete a customer:**
```
delete 1
```
Expected output: The 1st customer in the list is deleted.

**Step 6 - Exit the app:**
```
exit
```
Expected output: `Goodbye! Exiting ClientEase. You have <N> customer(s) saved.`

> [!TIP]
> All your data is saved automatically after every command. You never need to press a "Save" button.

---

## Features

### Notes on Command Format

> **Read this before using any command.**

- Words in `UPPER_CASE` are **parameters you supply**. For example, in `add name/NAME`, replace `NAME` with the actual
  name, e.g. `add name/John Doe`.
- Items in `[square brackets]` are **optional**. For example, `name/NAME [contact/CONTACT]` can be used with or without
  a contact.
- Parameters can be entered **in any order**. For example, `add name/John Doe contact/98765432` is the same as
  `add contact/98765432 name/John Doe`.
- Commands that take no parameters (e.g. `help`, `list`, `exit`) will **ignore any extra text** you type after them.
- If you are using a **PDF version** of this guide, be careful when copying multi-line commands — line breaks may cause
  spaces to be omitted.

---

### Viewing Help : `help`

Opens a help window that provides a quick overview of available commands and a link to the full User Guide.

![help message](images/helpMessage.png)

**Format:** `help`

### Notes

- The help window does **not block** the main application — you can continue using ClientEase while it is open.
- If the help window is already open, running `help` again will focus on the existing window.
- Command ignore extra space

> [!TIP]
> Use the help window as a quick reference when you forget command formats, instead of searching through the full guide.

---

### Adding a Customer : `add`

Adds a new customer record to ClientEase.

**Format:**
```
add name/NAME [products/PRODUCTS] [location/LOCATION] [deadline/DEADLINE] [contact/CONTACT]
```

**Parameter details:**

| Parameter | Required? | Notes                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
|---|---|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `name/NAME` | Yes | 1-100 characters after trimming and space normalisation. Only ASCII letters (A-Z), spaces, `.`, `'`, and `-`. Must contain at least one letter. Names are unique case-insensitively and with repeated spaces collapsed.                                                                                                                                                                                                                                                          |
| `products/PRODUCTS` | No | Comma-separated list of 1-5 items chosen from: Muffin, Chocolate Cake, Vanilla Cake, Brownie, Cookie. Items can optionally include a quantity using a colon (e.g., Muffin:3). Matching is case-insensitive. Empty items are invalid.                                                                                                                                                                                                                                             |
| `location/LOCATION` | No | Non-blank after trimming. Maximum length 200 characters.                                                                                                                                                                                                                                                                                                                                                                                                                         |
| `deadline/DEADLINE` | No | Accepted formats: `yyyy-MM-dd HH:mm`, `yyyy-MM-dd`, `dd/MM/yyyy`. Entries without a time default to **23:59**.                                                                                                                                                                                                                                                                                                                                                                   |
| `contact/CONTACT` | No | Semicolon-separated entries. Each entry must be either an 8-digit local phone number or an international phone in `+<2-3 digit country code><1-12 digit number>` format; spaces in phone numbers are ignored. Emails are up to 100 characters, must start with an alphanumeric character, contain only letters, digits, dots, and hyphens, and contain exactly one `@` with an alphanumeric at the start of the domain. Empty entries (e.g. trailing or double `;`) are invalid. |

> [!IMPORTANT]
> **Priority Tagging:**
> ClientEase automatically tags customers with a priority color based on the **total quantity** of products:
> * **Green (Low):** 1–5 total items
> * **Yellow (Medium):** 6–10 total items
> * **Red (High):** 11 or more total items

**Other constraints:**

- Shorthands `n/`, `p/`, `l/`, `d/`, and `c/` are accepted.
- Each prefix can appear at most once.
- Unrecognised `<word>/` prefixes are rejected.
- Optional fields can be omitted.
- If a prefix is provided with no value (e.g. `products/`), the field is treated as empty.
- Non-ASCII characters (e.g. Chinese) are rejected in `name/`, `products/`, and `contact/`. `location/` currently accepts
  any characters as long as it is non-blank and within the length limit.

> [!WARNING]
> If you try to add a customer with a name that already exists (case-insensitive, spaces normalised),
> ClientEase will reject the entry and display an error. Check the existing list with `list` before adding.

**Products are listed as a numbered list under each customer card.**

**Examples:**

```
add name/John Doe contact/98765432;johnd@example.com products/Chocolate Cake:2, Muffin:5 location/Clementi Ave 2 deadline/2025-12-31
```
Adds a customer named John Doe, with two unique products with specified quantities, a location, a deadline of 31 Dec 2025 at 23:59, and two contact
details.

```
add name/Sarah Lim
```
Adds a customer named Sarah Lim with no other details. You can `edit` to fill in the rest later.

---

### Listing All Customers : `list`

Shows a list of all persons in ClientEase.

**Format:** `list`

---

### Editing a Customer : `edit`

Edits an existing person in ClientEase.

**Format:**
```
edit INDEX [name/NAME] [products/PRODUCTS] [location/LOCATION] [deadline/DATE] [contact/CONTACT]
```

Short prefixes are supported: `n/` for `name/`, `p/` for `products/`, `l/` for `location/`, `d/` for `deadline/`, and `c/` for `contact/`.

- Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- Products must be chosen from the placeholder list: Muffin, Chocolate Cake, Vanilla Cake, Brownie, Cookie.

**Examples:**

- `edit 1 contact/91234567` — Edits the contact of the 1st person to be `91234567`.
- `edit 2 name/Betsy Crower products/Muffin location/Newgate Prison` — Edits the name, products, and location of the 2nd person.

---

### Locating Customers : `find`

Finds persons whose names contain any of the given keywords.

**Format:**
```
find KEYWORD [MORE_KEYWORDS]
```

- The search is case-insensitive. e.g. `hans` will match `Hans`
- The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
- Only the name is searched.
- Only full words will be matched e.g. `Han` will not match `Hans`
- Persons matching at least one keyword will be returned (i.e. `OR` search). e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

**Examples:**

- `find John` returns `john` and `John Doe`
- `find alex david` returns `Alex Yeoh`, `David Li`

![result for 'find alex david'](images/findAlexDavidResult.png)

---

### Deleting a Customer : `delete`

Deletes a customer from ClientEase using either their displayed index or exact name.

**Format:**
```
delete INDEX
```

- Deletes the person at the specified `INDEX`.
- The index refers to the index number shown in the displayed person list.
- The index **must be a positive integer** 1, 2, 3, …

**Examples:**

- `list` followed by `delete 2` deletes the 2nd customer in ClientEase.
- `find Betsy` followed by `delete 1` deletes the 1st customer in the results of the `find` command.

```
delete NAME
```

- Deletes the customer with the given `NAME`.
- Matching is **case-insensitive** and ignores extra spaces.

**Examples:**

- `delete John Doe` `delete john doe` `delete John Doe [extra space]` All delete the same customer 'John Doe'

---

### Clearing All Customers : `clear`

Clears all entries from ClientEase.

**Format:** `clear`

> [!WARNING]
> This action is irreversible and will permanently delete all customer records. Consider backing up `data/ClientEase.json` (see [Saving the Data](#saving-the-data)) before running this command.

---

### Exiting the App : `exit`

Exits the program after displaying a farewell message with the current customer count.

**Format:** `exit`

- A farewell message will be displayed: `Goodbye! Exiting ClientEase. You have <N> customer(s) saved.`
- The app will close automatically after a short delay.

---

## Saving the Data

ClientEase **automatically saves** your data to disk after every command that changes data. There is no Save button and
no need to save manually.

Your data is stored at:
```
[home folder]/data/ClientEase.json
```

---

## Editing the Data File

Advanced users may edit the data file directly using any text editor.

> [!CAUTION]
> If your changes to the data file makes its format invalid, ClientEase will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
>
> Furthermore, certain edits can cause ClientEase to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.

---

## FAQ

**Q: How do I transfer my data to another computer?**

A: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ClientEase home folder.

---
**Q: Can I have two customers with the same name?**

A: No. ClientEase treats names as unique identifiers (case-insensitive, spaces normalised). If two customers share a
name, consider differentiating them, e.g. `John Doe (Clementi)` and `John Doe (Tampines)`.

---

## Known Issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---

## Command Summary

| Action | Format | Example                                                                                              |
|---|---|------------------------------------------------------------------------------------------------------|
| **Help** | `help` | `help`                                                                                               |
| **Add** | `add name/NAME [products/PRODUCTS] [location/LOCATION] [deadline/DEADLINE] [contact/CONTACT]` | `add name/John Doe contact/98765432;johnd@example.com products/Chocolate Cake:5 deadline/2025-12-31` |
| **List** | `list` | `list`                                                                                               |
| **Edit** | `edit INDEX [name/NAME] [products/PRODUCTS] [location/LOCATION] [deadline/DATE] [contact/CONTACT]` | `edit 2 name/James Lee contact/jameslee@example.com`                                                 |
| **Find** | `find KEYWORD [MORE_KEYWORDS]` | `find James Jake`                                                                                    |
| **Delete** | `delete INDEX` | `delete 3`                                                                                           |
| **Clear** | `clear` | `clear`                                                                                              |
| **Exit** | `exit` | `exit`                                                                                               |

> [!TIP]
> **Shorthand Prefixes:**
> For the `add` and `edit` commands, you can use shorter versions of the prefixes to save time:
> * `n/` instead of `name/`
> * `p/` instead of `products/`
> * `l/` instead of `location/`
> * `d/` instead of `deadline/`
> * `c/` instead of `contact/`
>
> *Example:* `add n/John Doe p/Muffin` is equivalent to `add name/John Doe products/Muffin`.

---

## Glossary

| Term | Definition |
|---|---|
| **CLI** | Command Line Interface - a text-based way of interacting with software by typing commands |
| **GUI** | Graphical User Interface - the visual window of the app |
| **Customer** | A record representing a person who has placed or may place an order with your business |
| **Command** | A text instruction you type into the command box to perform an action |
| **Parameter** | A piece of information supplied alongside a command, e.g. `name/John Doe` |
| **Prefix** | The short label before a parameter value, e.g. `name/`, `products/`, `contact/` |
| **Index** | The 1-based number shown beside each customer in the displayed list |
| **Deadline** | A date (and optional time) representing when an order is due |
| **Contact** | Consolidated contact details (phone and/or email) for a customer, separated by semicolons |
| **Product** | An item associated with a customer's order, listed under Products |
| **Home folder** | The folder where `clientease.jar` and the `data/` directory are stored |
| **JSON file** | The data file (`ClientEase.json`) where ClientEase stores all customer records |
