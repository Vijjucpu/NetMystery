# NetMystery
## Intelligent Network Fault Diagnosis and Troubleshooting Simulator

NetMystery is a beginner-friendly desktop application developed using Java Swing. It helps students learn basic network troubleshooting by investigating common network problems, examining sample evidence, selecting a diagnosis, and receiving feedback.

## Features

- Five network troubleshooting scenarios.
- Suggested networking tools: `ping`, `ipconfig`, `nslookup`, and `tracert`.
- Simulated command output and diagnostic evidence.
- Diagnosis selection with feedback.
- Score tracking with a maximum score of 50 points.
- Previous and Next navigation between cases.
- Return to cases without losing investigation progress.
- Final performance report.
- Try Again option to restart the investigation.

## Technologies Used

- **Programming Language:** Java
- **GUI Framework:** Java Swing
- **Libraries:** AWT and Swing
- **Concepts:** Event-driven programming, GUI design, and case-based troubleshooting.
- **Data:** Predefined scenarios and simulated diagnostic outputs.

## System Requirements

- Java Development Kit (JDK) installed.
- Windows, Linux, or another compatible desktop operating system.
- VS Code or any Java-supported IDE (optional).

Check Java installation using:

```bash
java -version
javac -version
```

## Project Structure

```text
NetMystery/
├── NetMystery.java
├── NetMystery.class
└── README.md
```

- `NetMystery.java` – Java source code.
- `NetMystery.class` – Compiled Java class file.
- `README.md` – Project documentation.

## Installation and Execution

Follow these steps to run the project:

1. Download and extract the project ZIP file.
2. Open the extracted project folder in VS Code or a terminal.
3. Open the terminal in the folder containing `NetMystery.java`.
4. Compile the Java program:

```bash
javac NetMystery.java
```

5. Run the application:

```bash
java NetMystery
```

The NetMystery application window should open.

## How to Use

1. Start the application.
2. Select a network troubleshooting case.
3. Read the problem scenario carefully.
4. Open the suggested networking tool.
5. Examine the simulated command output.
6. Return to the case and select the appropriate diagnosis.
7. Check your answer and review the feedback.
8. Use Previous and Next to navigate between cases.
9. Complete all five scenarios and review your final score and report.
10. Select Try Again to restart the investigation.

## Network Troubleshooting Scenarios

| No|       Scenario               |             Suggested Tool |
|---|
| 1 | Website Not Opening | `ping` |
| 2 | Domain Name Not Resolving    |                 `nslookup` |
| 3 | Network Connection Failure   |                 `ipconfig` |
| 4 | Slow Network Connection      |                     `ping` |
| 5 | Incorrect Network Route      |                  `tracert` |

These tools demonstrate common troubleshooting concepts through sample output. The results are simulated and do not represent the actual network status of the computer.

## Testing

The following checks can be performed:

- Verify that the application launches successfully.
- Navigate through all five scenarios.
- Check diagnosis answers and score updates.
- Open the suggested tools and inspect their sample outputs.
- Verify that investigation progress is preserved when returning to a case.
- Complete the cases and review the final report.
- Restart the application using Try Again.

## Advantages

- Beginner-friendly graphical interface.
- Practical, case-based learning.
- Clear diagnostic tool recommendations.
- Immediate feedback for selected answers.
- Score tracking to encourage learning.
- Safe practice without changing real network settings.

## Limitations

- Command outputs are simulated.
- The application does not perform live network diagnosis.
- Only five scenarios are included.
- Diagnoses and feedback depend on predefined case data.

## Future Enhancements

- Add more network troubleshooting scenarios.
- Include hints and detailed explanations.
- Improve the formatting of diagnostic evidence.
- Add a downloadable performance report.
- Introduce more networking concepts and advanced cases.

## Conclusion

NetMystery makes basic network troubleshooting easier to understand through guided practice. It combines network problem scenarios, suggested diagnostic tools, simulated evidence, diagnosis feedback, and score tracking in one Java Swing application.

## Project Author

- **G. Vijayalaxmi** – 25075A7202

**Project Title:** NetMystery – Intelligent Network Fault Diagnosis and Troubleshooting Simulator

**Project Type:** Course-Based Project
