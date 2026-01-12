# Weight-Converter-Java

This application is a command-line Weight Converter that provides a comprehensive menu-driven system for converting between various weight units (pounds, kilograms, ounces, grams, and stones). It features robust input validation, a persistent conversion history, and multiple conversion modes to suit different user needs, all built with a focus on clarity and user-friendly interaction.

Key Features:

Multiple Conversion Modes:

Standard Menu: Step-by-step guided conversions for precise control.

Quick Convert: Allows natural-language input (e.g., "150 lbs") for instant results.

Batch Processing: Converts multiple comma-separated values in one operation.

Data Management & History:

Maintains an in-memory list of all conversions performed during the session.

Allows users to view the complete history or clear it with a confirmation prompt.

User Experience Enhancements:

Input Validation: Checks for negative numbers, unrealistic values, and invalid formats.

Configurable Precision: Offers settings to control the number of decimal places in results.

Educational Reference: Includes a built-in unit reference guide with conversion factors.

Interactive Prompts: Confirms actions and asks if the user wants to perform another conversion.

Code Structure & Maintainability:

Uses final constants for conversion factors, ensuring accuracy and easy updates.

Employs helper methods to avoid code duplication (e.g., for input validation).

Implements a clear, modular menu system using a switch statement in the main loop.

