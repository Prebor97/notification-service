# Notification Service

## Overview

The Notification Service is a microservice responsible for handling all notifications in the application ecosystem. It uses JavaMailSender to send email notifications to users based on events triggered by other services. This service listens to Kafka topics for notification requests and processes them asynchronously, ensuring reliable and scalable notification delivery without blocking other services.

**Key responsibilities**:
- Sending email notifications for various events such as ticket booking confirmations, payment status updates, event creation alerts, and user-related notifications (e.g., registration confirmation, password resets).
- Integrating with other services via Kafka for event-driven communication.

This service does not maintain its own database, as it relies on transient event data from Kafka messages.

## Features

- **Email Notifications**: Supports sending plain text and HTML emails using configurable templates.
- **Event-Driven Architecture**: Subscribes to Kafka topics to receive notification triggers from services like User Service, Events Service, Recon Service, and Payment Service.
- **Asynchronous Processing**: Handles notifications in the background to ensure high throughput.
- **Configurable**: Easily configurable SMTP settings, email templates, and Kafka consumer properties.
- **Error Handling**: Retries failed email sends and logs errors for monitoring.

## Technologies Used

- **Java**: Core language (JDK 17+ recommended).
- **Spring Boot**: Framework for building the microservice.
- **Spring Kafka**: For consuming messages from Kafka topics.
- **JavaMailSender**: Spring's abstraction for sending emails via SMTP.
- **Lombok**: For reducing boilerplate code (optional).
- **Maven**: Build tools.
- **Kafka**: Message broker for inter-service communication.

## Prerequisites

- Java 17 or higher installed.
- Kafka cluster running (e.g., local setup or cloud-based like Confluent).
- SMTP server credentials (e.g., Gmail, SendGrid, or custom SMTP) for email sending.
- Access to other services' Kafka topics for testing.

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Prebor97/notification-service.git
   cd notification-service

2. **Build the Project using maven**:
   ```bash
   mvn clean install

3. **Run the Service**
   ```bash
   java -jar target/notification-service.jar
   ```
   Or using Spring Boot Maven plugin:
   ```bash
   mvn spring-boot:run
   ```
## Usage

This section outlines how the Notification Service operates and integrates with Kafka to process and send notifications.

## How It Works

### Kafka Integration

- The service utilizes a `@KafkaListener` to consume messages from a configured Kafka topic.
- Upon receiving a message, the service:
    1. Parses the message payload.
    2. Prepares the email content, utilizing templates.
    3. Sends the email using `JavaMailSender`.

### Sending Notifications

- Other services, such as the **Recon Service**, publish events to Kafka after specific actions (e.g., checking payment status).
- **Example**:
    - The Recon Service detects a successful payment and publishes a message with the type `PAYMENT_SUCCESS`.
    - The Notification Service consumes this message and sends an email with content like:
      > "Your payment for ticket #123 is successful."  
    
## Project Structure
```plaintext
java/
└── com/
    └── ticket/
        └── app/
            └── notification_service/
                ├── config/
                │   └── KafkaConfig.java
                │
                ├── eventDto/
                │   ├── AdminCreatedEvent.java
                │   ├── PasswordResetRequestEvent.java
                │   ├── UserDeletedEvent.java
                │   ├── UserLoggedEvent.java
                │   ├── UserRegisteredEvent.java
                │   └── UserRoleUpdatedEvent.java
                │
                ├── service/
                │   ├── ConsumerService.java
                │   ├── EmailService.java
                │   └── EventProcessing.java
                │
                └── NotificationServiceApplication.java
```
## Description
- **`src/main/java/com/ticket/app/notification_service/`**: Contains the core Java source code for the application.
    - `NotificationApplication.java`: The main Spring Boot application class that bootstraps the service.
    - `config/`: Configuration classes for setting up Kafka and other dependencies.
    - `service/`: Service classes implementing the logics for everything involving sending notifications (e.g., via email).
    - `eventDto/`: DTOs defining the structure of message payloads.

- **`src/main/resources/`**: Static resources and configuration files.
    - `application.properties`: Configuration file for application settings, such as Kafka brokers and email server details.
    - `templates/`: Directory for email templates (e.g., HTML or plain text files).


