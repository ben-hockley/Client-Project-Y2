let recipients = []; // This will store the fetched emails

// Wait for the DOM to load before running the script
document.addEventListener('DOMContentLoaded', async function () {
    const emailToAllCheckbox = document.getElementById('sendToAll');
    const openOutlookButton = document.getElementById('openOutlookButton');
    const subjectField = document.getElementById('subject');
    const bodyField = document.getElementById('body');

    // Fetch emails when the page loads (default state: emailToAll=false)
    await fetchEmails(false);

    // Fetch emails when the "Send to All" checkbox is toggled
    emailToAllCheckbox.addEventListener('change', async function () {
        const emailToAll = this.checked; // true or false based on checkbox state
        console.log("Checkbox toggled. emailToAll:", emailToAll); // Debugging log
        await fetchEmails(emailToAll);
    });

    // Fetch function for emails
    async function fetchEmails(emailToAll) {
        try {
            const response = await fetch(`/newsletter/getEmails?emailToAll=${emailToAll}`);
            if (!response.ok) {
                console.error('Error fetching emails:', response.statusText);
                return; // Stop further processing if the response is not OK
            }
            const data = await response.json();
            recipients = data; // Store fetched emails in recipients array

            // Log the recipients array immediately after fetching
            console.log("Fetched emails:", recipients); // Debugging log
        } catch (error) {
            console.error('Error fetching emails:', error);
            recipients = []; // Clear recipients on error
        }
    }

    // Event listener for the "Open Outlook" button
    openOutlookButton.addEventListener('click', function () {
        const subject = subjectField.value.trim(); // Get the subject value
        const body = bodyField.value.trim(); // Get the body content
        const emailToAll = emailToAllCheckbox.checked; // Get the checkbox state

        // Validate that subject and body are not empty
        if (!subject) {
            alert("Please enter a subject.");
            return;
        }
        if (!body) {
            alert("Please enter the body content.");
            return;
        }

        // If no recipients are available, do not proceed
        if (recipients.length === 0) {
            alert("No recipients available.");
            console.log("No recipients available.");
            return;
        }

        // Prepare the recipients for the BCC field
        const bccRecipients = emailToAll ? recipients.join(';') : recipients.join(';');

        // Construct the mailto link
        const mailtoLink = `mailto:?bcc=${bccRecipients}&subject=${encodeURIComponent(subject)}&body=${encodeURIComponent(body)}`;

        // Log the mailto link for debugging
        console.log("Constructed mailto link:", mailtoLink);

        // Open the mail client (Outlook or default)
        window.location.href = mailtoLink;
    });
});
