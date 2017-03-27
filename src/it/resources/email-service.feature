# language: en_US

Feature: Email Service with Failover on providers

Scenario: Happy Path, email sent
  Given the email subject "Testing Email"
  And the body is
  """
  Frodo saw him to the door. He gave a final wave of his hand, and walked off at
  a surprising pace; but Frodo thought the old wizard looked unusually bent,
  almost as if he was carrying a great weight. The evening was closing in, and
  his cloaked figure quickly vanished into the twilight. Frodo did not see him
  again for a long time.
  """
  And the email address destination list is
  | name  | email           |
  | Jorge | jorge@test.com  |
  | Mario | mario@test.com  |
  When the email service is called with the give data
  Then the response status is 200


Scenario: Wrong email addresses
Given the email subject "Testing Email"
And the body is
"""
  Frodo saw him to the door. He gave a final wave of his hand, and walked off at
  a surprising pace; but Frodo thought the old wizard looked unusually bent,
  almost as if he was carrying a great weight. The evening was closing in, and
  his cloaked figure quickly vanished into the twilight. Frodo did not see him
  again for a long time.
  """
And the email address destination list is
| name  | email           |
| Jorge | @test.com       |
| Mario | mario.test.com  |
When the email service is called with the give data
Then the response status is different than 200


Scenario: Empty email addresses
Given the email subject "Testing Email"
And the body is
"""
  Frodo saw him to the door. He gave a final wave of his hand, and walked off at
  a surprising pace; but Frodo thought the old wizard looked unusually bent,
  almost as if he was carrying a great weight. The evening was closing in, and
  his cloaked figure quickly vanished into the twilight. Frodo did not see him
  again for a long time.
  """
And the email address destination list is
| name  | email           |
When the email service is called with the give data
Then the response status is different than 200
