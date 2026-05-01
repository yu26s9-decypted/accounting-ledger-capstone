# Stash: Java Accounting Ledger

<img width="1440" height="565" alt="stash-githubhero" src="https://github.com/user-attachments/assets/0e934ccd-057d-44f8-864d-64f3a2035417" />

<h2><img height="20" src="/img/stash-log-cornerrounded.svg">&nbsp;&nbsp;What is Stash?</h2>

Stash is a command line interface (CLI) transaction ledger built in java. Stash allows you to track financial transactions, add and deposit payments, get AI summaries and view ledger reportings.

<img width="1103" height="562" alt="Screenshot 2026-05-01 at 8 20 03 PM" src="https://github.com/user-attachments/assets/82ce0393-aeea-4f29-8328-315ccd1dd3ee" />


**Key features of Stash includes:**

- **Deposits** — Record incoming transactions with an amount, description, vendor, date, and time
- **Payments** — Log a outgoing payments
- **Ledger view** — Browse all transactions, or filter by deposits or payments.
- **Vendor search** — Filter a transactions by vendor
- **Transaction CSV** - Transactions are read and written to via a csv file in the ``files`` folder

### File Tree
```
accounting-ledger-capstone/
├── img/
├── files/
├── ├──transaction.csv
└── src/
    └── main/
        └── java/
            └── com.pluralsight/
                ├── data/
                │   ├── Transaction.java
                │   └── TransactionFileManager.java
                └── ui/
                    ├── Console.java
                    ├── LedgerMenuOption.java
                    ├── PrintFormatUtility.java
                    └── Main.java
                    └── AiService.java

```

## How to run

1. Clone the repo by running the command in your Command line interface (CLI) `git clone https://github.com/your-username/accounting-ledger-capstone.git`
2. Open in IntelliJ IDEA or any compatible IDE
3. Run `Main.java` 

## How to navigate the Stash CLI

Once the program is running, you can navigate the menu using the keyboard shortcuts:

- `D` Add a deposit
- `P` Make a payment
- `L` Open the ledger
- `S` Get the AI summarizer. (Add your own API key to the env from OpenRouter)
- `X` Exit the application

## Dev Note:

During this capstone project, I’ve gained alot of important takeaways that will change how I approach future projects.

One of the biggest lessons for me was how I approach a project from the beginning. Initially, I focused heavily on getting the core requirements working as quickly as possible, without spending enough time thinking through edge cases or alternative user inputs. As a result, I ended up having to refactor several methods multiple times after they were already written. This slowed down development and made my code harder to maintain than it needed to be for myself and I can now see the importance of writting code that makes it easy for others and myself to work on at a later date.

Moving forward, My takeaway from this capstone is that it is much more efficient to invest additional time at the start of a project to plan more thoroughly. Creating a more detailed user story, flow diagrams, and identifying potential edge cases ahead of time would help reduce the amount of rework later compared to my approach with the goal of creating methods with validation and error handling in mind from the beginning, rather than treating them as an afterthought like I did for this capstone.

Another major challenge I encountered was related to program structure. As my codebase grew, it became increasingly difficult to debug and manage because I placed too much responsibility inside a single main class. That class ended up handling user interface logic, transaction processing, file management, filtering, and parsing all in one place. This made the code harder to read, harder to test, and more error-prone as I try to add better validation logics & new features.

A specific issue I ran into was infinite loops when using while loops combined with try-catch blocks, especially when allowing users to exit input using an “X” key. During refactoring the loop conditions were not correctly placed, which caused the program to repeatedly prompt the user or fail to exit as what I had intended. After reviewing the structure, I realized the problem all along was where logic was placed during my refactoring.

This capstone ultimately helped me understand that writing functional code is simply only one part of development. It is just as important or even more important to take the time before just diving into the coding part to designing maintainable code, anticipate edge cases early, and structuring logic in a way that makes debugging and future refactoring alot easier.










