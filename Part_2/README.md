# Assignment 5 – Part 2: UI Testing with Playwright

## Project Overview
This project implements automated UI testing for the DePaul University bookstore using Java, JUnit, and Playwright. The bookstore workflow was completed in two ways:

1. Manual UI testing using Java + Playwright
2. AI-assisted UI testing using the Playwright MCP agent

Both versions automate the same general bookstore workflow:
- search for earbuds
- apply filters
- open the JBL product page
- verify product details
- add the item to the cart
- verify the shopping cart contents

## GitHub Actions Workflow
GitHub Actions workflow/run link: (https://github.com/alberto1996-23/SE-333-A5/actions/runs/23102662841)

The GitHub Actions workflow runs automatically on pushes to the main branch. It includes static analysis, test execution, and coverage/report artifacts. My workflow completed successfully and generated the required uploaded artifacts, including Checkstyle and JaCoCo results.

## Video Recording
The Playwright test execution video was recorded from start to finish as required for Part 2.

## Reflection: Manual UI Testing vs AI-Assisted UI Testing

For this assignment, I completed the same DePaul bookstore UI workflow using two approaches: manual UI testing and AI-assisted UI testing. In the manual approach, I wrote the test directly in Java using Playwright and JUnit. In the AI-assisted approach, I used the Playwright MCP agent to generate a Playwright test from a natural language description of the workflow. Both approaches ultimately tested a similar bookstore scenario, but the process of creating and stabilizing the tests was very different.

The manual UI test took more effort at the beginning because I had to write the logic, selectors, waits, and assertions myself. However, this gave me more control over how the test behaved. I could carefully choose the selectors that matched visible elements on the page and adjust them when the website behaved unexpectedly. Once the manual test was working, it felt more predictable and easier to trust because I understood exactly why each line of code was there.

The AI-assisted UI test was faster to generate initially. Using Playwright MCP, I was able to describe the workflow in natural language and get a Java JUnit Playwright test file created quickly. This made the setup feel easier and more convenient. However, the generated code was not fully reliable right away. Several selectors were too broad, targeted hidden elements, or used text matches that caused the test to fail. Because of that, I still had to manually debug the generated code, replace unstable selectors, and rerun the test multiple times before it passed consistently.

In terms of accuracy and reliability, the manual test ended up being more dependable from the start because it was built with more deliberate selector choices. The MCP-generated test was useful as a starting point, but it required human correction before it became stable. This showed me that AI can help speed up test creation, but it does not eliminate the need for careful testing knowledge and debugging skills.

For maintenance effort, I think the manual test may be easier to maintain because I fully understand its structure and purpose. The AI-generated test can still be useful, but if the UI changes, it may be harder to know why a certain generated locator was chosen unless I review and clean it up myself. One of the biggest limitations I encountered with the AI-assisted approach was that it often selected hidden or ambiguous elements, which caused timeout errors and flaky behavior. This made it clear that AI-assisted UI testing is helpful, but still needs human oversight to be production-ready.

Overall, this assignment showed me that manual UI testing offers more control and reliability, while AI-assisted UI testing offers speed and convenience. The best result came from combining both approaches: using AI to accelerate the initial creation of the test, then manually refining it so that it actually works correctly.
For this assignment, I completed the same DePaul bookstore UI workflow using two approaches: manual UI testing and AI-assisted UI testing. In the manual approach, I wrote the test directly in Java using Playwright and JUnit. In the AI-assisted approach, I used the Playwright MCP agent to generate a Playwright test from a natural language description of the workflow. Both approaches ultimately tested a similar bookstore scenario, but the process of creating and stabilizing the tests was very different.

The manual UI test took more effort at the beginning because I had to write the logic, selectors, waits, and assertions myself. However, this gave me more control over how the test behaved. I could carefully choose the selectors that matched visible elements on the page and adjust them when the website behaved unexpectedly. Once the manual test was working, it felt more predictable and easier to trust because I understood exactly why each line of code was there.

The AI-assisted UI test was faster to generate initially. Using Playwright MCP, I was able to describe the workflow in natural language and get a Java JUnit Playwright test file created quickly. This made the setup feel easier and more convenient. However, the generated code was not fully reliable right away. Several selectors were too broad, targeted hidden elements, or used text matches that caused the test to fail. Because of that, I still had to manually debug the generated code, replace unstable selectors, and rerun the test multiple times before it passed consistently.

In terms of accuracy and reliability, the manual test ended up being more dependable from the start because it was built with more deliberate selector choices. The MCP-generated test was useful as a starting point, but it required human correction before it became stable. This showed me that AI can help speed up test creation, but it does not eliminate the need for careful testing knowledge and debugging skills.

For maintenance effort, I think the manual test may be easier to maintain because I fully understand its structure and purpose. The AI-generated test can still be useful, but if the UI changes, it may be harder to know why a certain generated locator was chosen unless I review and clean it up myself. One of the biggest limitations I encountered with the AI-assisted approach was that it often selected hidden or ambiguous elements, which caused timeout errors and flaky behavior. This made it clear that AI-assisted UI testing is helpful, but still needs human oversight to be production-ready.

Overall, this assignment showed me that manual UI testing offers more control and reliability, while AI-assisted UI testing offers speed and convenience. The best result came from combining both approaches: using AI to accelerate the initial creation of the test, then manually refining it so that it actually works correctly.