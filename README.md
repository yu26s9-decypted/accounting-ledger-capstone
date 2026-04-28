# Stash: Java Accounting Ledger

<img width="1440" height="565" alt="stash-githubhero" src="https://github.com/user-attachments/assets/0e934ccd-057d-44f8-864d-64f3a2035417" />

<h2><img height="20" src="/img/stash-log-cornerrounded.svg">&nbsp;&nbsp;What is Stash?</h2>

Stash is a account ledger built in Java.

**Key features of Stash includes:**

- **Deposits** — Record incoming transactions with an amount, description, vendor, date, and time
- **Payments** — Log a outgoing payments
- **Ledger view** — Browse all transactions, or filter by deposits or payments.
- **Vendor search** — Filter a transactions by vendor

### File Tree
```
accounting-ledger-capstone/
├── img/
└── src/
    └── main/
        └── java/
            └── com.pluralsight/
                ├── data/
                │   ├── Transaction.java
                │   ├── transaction.csv
                │   └── TransactionFileManager.java
                └── ui/
                    ├── Console.java
                    ├── LedgerMenuOption.java
                    ├── PrintFormatUtility.java
                    └── Main.java
```

## How to run

1. Clone the repo by running the command in your Command line interface (CLI) `git clone https://github.com/your-username/accounting-ledger-capstone.git`

2. Open in IntelliJ IDEA or any compatible IDE

3 Run `Main.java`

## How to navigate the Stash CLI

Once the program is running, you can navigate the menu using the keyboard shortcuts:

- `D` — Add a deposit
- `P` — Make a payment
- `L` — Open the ledger
- `S` - Get the AI summarizer. (Add your own enviromental ENV from OpenRouter if you want to use this)
- `X` — Exit the application



