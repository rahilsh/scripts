#!/bin/bash

# Replace with your actual values
QUEUE_URL="https://sqs.us-east-1.amazonaws.com/<account_id>/<queue_name>"
MESSAGE_LOG_FILE="sqs_messages.log"

# Check if required tools are installed
if ! command -v aws &> /dev/null; then
    echo "AWS CLI is not installed. Please install it and try again."
    exit 1
fi

if ! command -v jq &> /dev/null; then
    echo "jq is not installed. Please install it to parse JSON."
    exit 1
fi

# Function to process a single message
process_message() {
  receipt_handle=$1

  # Try deleting the message (add error handling to suit your needs)
  if aws sqs delete-message --queue-url "$QUEUE_URL" --receipt-handle "$receipt_handle"; then
    echo "Message deleted successfully"
  else
    echo "Error deleting message. Handle: $receipt_handle"
  fi
}

# Function to receive and process messages
receive_and_process_messages() {
  output=$(aws sqs receive-message --queue-url "$QUEUE_URL" --max-number-of-messages 10 --wait-time-seconds 5 --output json)

  # Check if any messages were received
  if [ -z "$output" ] || [[ "$output" == 'null' ]]; then
    echo "No messages in queue. Exiting."
    exit 0  # Terminate the script
  fi

  # Save messages to file and process
  echo "$output" >> "$MESSAGE_LOG_FILE"
  echo "$output" | jq -c '.Messages[]' | while read -r message; do
    receipt_handle=$(echo "$message" | jq -r .ReceiptHandle)

    # Check that we extracted a valid handle
    if [ -n "$receipt_handle" ]; then
        process_message "$receipt_handle"
    else
        echo "Invalid message format. Missing ReceiptHandle."
    fi
  done
}

# Main execution loop
while true; do
  receive_and_process_messages
  sleep 5  # Optional delay between polling cycles
done
