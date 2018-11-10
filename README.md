# Wanaliser
Whatsapp conversation log analiser

This tool will allow you to juice the most relevant data about a Whatsapp conversation log.

## Already included features:
- N of users
- Date the conversation has been taken
- Total messages by: hour, day of week, user
- Total files by: user
- Average messages by: hour, day of week, user
- Average words per message by user
- Most active day of the conversation

## TODO:
- Natural Language analysis: to get a sentiment analysis, topic of a message, most talked topic in a period of time
- TF-IDF(Term frequency – Inverse document frequency): gets the most relevant words for a user
- Try to reduce TFIDF part execution time because of a large dataset. If calculations are well, the program for a 40k message 3 months conversation is doing roughly 2 billion operations. It took from 6:22pm to 10am next day to compute 54% of the set.
