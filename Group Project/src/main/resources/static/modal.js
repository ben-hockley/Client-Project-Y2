let recipients = [];  // This will store the fetched emails

// Fetch emails when the page loads
document.addEventListener('DOMContentLoaded', async function () {
    const emailToAllCheckbox = document.getElementById('sendToAll');
    const openOutlookButton = document.getElementById('openOutlookButton');

    // Fetch emails when the page loads (default state: emailToAll=false)
    await fetchEmails(false);

    // Fetch emails when checkbox is toggled
    emailToAllCheckbox.addEventListener('change', async function () {
        const emailToAll = this.checked;  // true or false based on checkbox state
        console.log("Checkbox toggled. emailToAll:", emailToAll);  // Debugging log
        await fetchEmails(emailToAll);
    });

    // Fetch function for emails
    async function fetchEmails(emailToAll) {
        try {
            const response = await fetch(`/getEmails?emailToAll=${emailToAll}`);
            if (!response.ok) {
                console.error('Error fetching emails:', response.statusText);
                return;  // Stop further processing if the response is not OK
            }
            const data = await response.json();
            console.log("Fetched emails:", data);  // Log emails for debugging
            recipients = data;  // Store fetched emails in recipients array
        } catch (error) {
            console.error('Error fetching emails:', error);
            recipients = [];  // Clear recipients on error
        }
    }

    // Event listener for the 'Open Outlook' button
    openOutlookButton.addEventListener('click', function () {
        const subject = document.getElementById('subject').value;  // Get the subject value
        const emailToAll = emailToAllCheckbox.checked;  // Get the emailToAll checkbox state
        const body = document.getElementById('body').value;  // Get the body content from the textarea
        let recipient = '';

        // Log the recipients before proceeding
        console.log("Form submitted. emailToAll:", emailToAll);
        console.log("Recipients before mailto link:", recipients);
        console.log("Body content:", body);

        // If no recipients are available, do not proceed
        if (recipients.length === 0) {
            console.log("No recipients available.");
            return;  // Stop if no recipients found
        }

        // If emailToAll is checked, use all the fetched recipients
        if (emailToAll) {
            recipient = recipients.join(';');  // Join emails with a semicolon for multiple recipients
        } else {
            // If emailToAll is unchecked, use the first email from the list
            recipient = recipients.join(';');
        }

        // Encode the subject and body to ensure they are properly formatted for a URL
        const encodedSubject = encodeURIComponent(subject);
        const encodedBody = encodeURIComponent(body);

        // Construct the mailto link with bcc
        let mailtoLink = `mailto:?bcc=${recipient}&subject=${encodedSubject}&body=${encodedBody}`;

        // Log the mailto link for debugging
        console.log("Constructed mailto link:", mailtoLink);

        // Open the mail client (Outlook or default)
        window.location.href = mailtoLink;
    });
});
