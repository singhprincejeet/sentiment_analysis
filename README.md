# CSI4107 Assignment 2 - Sentiment Analysis
### Authors
Shanzay Amjad - 7394483  
Princejeet Singh Sandhu - 7736952  

### Abstract

This study takes twitter tweets and explores how preprocessing the text and adding new features alongside a bag of words model can effect sentiment classification (positive, negative, objectve and neutral) with three different classifiers: Support Vector Machine (SVM), Naive Bayes (NB) and Decistion Tree (DT).  

### Division

The work division was set out by discussing the possible runs and additional features that could be added to improve final results. Each student worked on creating new features and performing the runs on their machines. Github was used to work in collaboration.  

### Program Structure & Functionality

The program contains the following Java classes:

##### Main Class
- ArffCreator - This class holds the main method used to generate an arff file to be used in Weka. The program uses the below helper classes in generating this file. The start of the class holds global boolean variables used for indicating whether preprocessing should be performed and if certain features should be included in the output arff file. These variables were used to create arff files with and without certain features to determine their effect on classificatoin. The generated file is sent to /out and named accordingly. To run the program, the main method from this class is run. 

##### Preprocessing Classes
- Stop Words - Class used for eliminating stop words from text.
- Stemmer - Porter Stemmer
- StringCleaner

##### Feature Classes
- SentimentWordCounter - This class uses the General Inquirer Lexicon and provides methods to count the number of positive and negative words found in a text. 
- PunctuationCounter - This class similarly counts the occurances of "?" and "!" in a text.  
- EmoticonCounter - This class used Christopher Potts' regex to extract and count the occurances of emoticons in a text. To further help with positive and negative classification, the extracted emoticons were labelled as having positive or negative sentiments. This was done by manually deciding categorizing certain emoticons as positive (e.g. :), :D, =) ) and certain emoticons as negative (e.g. :S, :(, :| ).  In implementation, only the mouth portion of the emoticon was considered for deciding a positive or negative emoticon.  
- ElongationCounter - This class determined and counted when words or punctuation was elongated (e.g. "nooooo"). To do this, if a character occurred or more times in a row, it was considered to be an elongation.  

### Resources 

When developing new features to help train the classifiers, the following resources were used:  
- Porter Stemmer
- Christopher Potts' emoticon regex
- General Inquirer Lexicon of pre-classified positive and negative words
- Stop word list as provided in Assignment 1  

### Step 1

The first part of this study explored training classifiers (SVM, NB and DT) on a simple bag of words model of the data. Pre-processing was preformed on the data to observe how this could affect classification results. The first experiment (Run 1) was performed on the provided arff file with no pre-processing and with the default settings provided in Weka. This was used as the benchmark for this section in order to compare changes. The subsequent runs use the generated arff file from our program. The following is a description of the pre-processing and settings on the arff file which the classifiers were trained on:  
 
 **Run 1** - Training performed on the provided arff file. No pre-processing: the benchmark.  
 **Run 2** - Training performed on the generated arff file with stemming (Porter's) and stop word removal (from Assignment 1).  
 **Run 3** - Training performed on the provided arff file with tf-idf (more weight on rare words), stemming  (IteratedLovinsStemmer) and stop word removal (Rainbow).  
 **Run 4** - Training performed on the genereated arff file with tf-idf (more wight on rare words), stemming (Porter's stemmer) and stop word removal (from assignment 1).  
 **Run 5** - Training performed on the genereated arff file with tf-idf (more wight on rare words), stemming (IteratedLovinsStemmer) and stop word removal (from assignment 1).   
 **Run 6** - Training performed on the generated arff file with less delimiters to include certain punctuation in the bag of words model. Only used [\r, \t, .] as delimiters. Continued using tf-idf (more wight on rare words), stemming (Porter's stemmer) and stop word removal (from assignment 1).  
 **Run 7** - Training performed on the generated arff file with less delimiters ([\r, \t, .]), tf-idf (more wight on rare words), stop word removal (from assignment 1), but no stemming.  

  **Table 1 - Summary of Step 1 Results (% Accuracy Only)**

|               |      SVM      |       NB      |       DT      |
| ------------- | ------------- | ------------- | ------------- |
| Run 1         |      49.1     |      43.7     |      43.2     |
| Run 2         |      48.6     |      44.5     |      45.7     |
| Run 3         |      49.0     |      36.1     |      44.5     |
| Run 4         |      48.3     |      33.2     |      44.5     |
| Run 5         |      49.2     |      34.6     |      44.0     |
| Run 6         |      47.3     |      44.1     |      45.1     |
| Run 7         |      48.0     |      44.4     |      45.5     |

### Step 2

The second part of this study explored adding other features to the training data and its effect on classification results. In Step 1, it was learned that tf-idf, stemming were having negative effect on accuracy. It was also concluded that using less delimiters, and allowing certain punctuation to be a part of the tokens in the bag of words model was improving classification accuracy. Hence these settings were applied to the provided arff and used as the benchmark for this section.  
  
This section explored the effect of adding the following features/attributes each individually to the training data:  
1. No features (benchmark)
2. (1) Positive and negative word counts  
   (2) Positive and negative word checks (a boolean if there are positive words; a boolean if there are negative words)
3. (1) "?" and "!" counts  
   (2) "?" and "!" checks (a boolean if there are "?"s; a boolean if there are "!"s)
4. (1) Positive and negative emoticon counts  
   (2) Positive and negative emoticon checks (a boolean if there are positive emoticons; a boolean if there are negative emoticons)  
5. (1) Elongated words/punctuation counts   
   (2) Elongated words/punctuation checks ((a boolean if there is elongation in the text)

For each feature, 3 runs were performed: (0) a combination of both counts and checks as features, (1) only the counts as a feature, (2) only the checks as a feature. These were compared to the benchmark, and the best out of the 4 were taken, i.e. either one of these three or the feature was not used if the benchmark performed better. The following runs were performed on 100 words instead of 1000 to save computation time. This lead to lower accuracies (especially SVM), but the results could still be compared relative to each other.  

  **Table 2 - Summary of Step 2 Results (% Accuracy Only)**

|                    |      SVM      |       NB      |       DT      |
| ------------------ | ------------- | ------------- | ------------- |
| Run 1              |      48.0     |      44.4     |      45.5     |
| Run 2              |      46.7     |      46.2     |      45.3     |
| Run 2.1            |      46.6     |      45.0     |      45.8     |
| Run 2.2            |      46.0     |      45.0     |      45.4     |
| Run 3              |      46.0     |      44.4     |      46.3     |
| Run 3.1            |      45.7     |      43.8     |      45.6     |
| Run 3.2            |      46.1     |      45.3     |      46.4     |
| Run 4              |      46.8     |      43.5     |      44.8     |
| Run 4.1            |      45.8     |      43.8     |      44.4     |
| Run 4.2            |      46.8     |      44.3     |      44.8     |
| Run 5              |      46.0     |      44.0     |      44.2     |
| Run 5.1            |      45.5     |      43.7     |      43.3     |
| Run 5.2            |      46.0     |      44.0     |      44.3     |
| Run 6              |      n/a      |      48.2     |      46.9     |
| Run 7              |      49.1     |      49.0     |      47.8     |
| Run 7 (1000 words) |      51.3     |      49.9     |      49.0     |

Run 6 was the combination of the features based on the best of each. For SVM, the benchmark was always better. For NB, 2 and 3.2 features were used (i.e. both positive and negative word counts and checks and only "?" and "!" checks). For DT, 2.1 and 3.2 features were used (i.e. only positive and negative word counts and "?" and "I" checks).  
  
Run 7 used each feature on the training data, but took the best of (0), (1) or (2) of that feature (i.e. both counts and checks, just the count or jsut the check.) Therefore on SVM, 2, 3.2, 4.2, 5.2 features were used, on NB, 2, 3.2, 4.2, 5.2 features were used and on DT, 2.1, 3.2, 4.2, 5.2 features were used. To break ties, the other classifiers and the benchmark values were also observed.  

The respective confusion matrices, precision, recall and f-measures are attached and similarly used for comparison.  
  
### Discussion

In the first section of this experiment, it was discovered that tf-idf had a negative effect on accuracies. This is because tf-idf gives importance to rare words and words that are useful in describing a text. This can be useful in keyword document retrieval, but not in classification. These rare words do not provide significance in the sentiment of a document. For this, the type of words (positive/negative) are more useful. The tf-idf addition seemed to have the worst effect on the Naive Bayes classifier. It was similarly noted that stemming was having a negative effect. This could be because elongated words provide significance in the sentiment of a document. Elongations indicated emphasis. Thirdly, the default tokenization provided by Weka included many punctuation as delimiters. This was used initially, but it was noted that including certain punctuation as part of the attributes of the document (bag of words), and using less delimiters improved classification. This is because, again, punctuation may not be relevant in document retrieval, which is better described with the words, it is important for the sentiment of the document. Punctuations and emoticons such as "!" and ":)" show there is strong or a happy feeling.  

Moving to the second portion of this experiment, four possible features were introduced as possible additions to the bag of words features, each having counts and booleans (8 features in total). The aim of this approach was to understand that certain features may be better as booleans and same may be better as exact counts of the occurance of that feature. Other features may create the best training when used in combination. The first added feature was positive and negative word counts as taken from the General Inquirer Lexicon. Initially it was assumed this would substantially improve classification, but these were indicators for negative and positive tweets, and not objective and neutral tweets, so many tweets were classified incorrectly. This could be further improved by creating a threshold that there must be a certain amount of positive or negative words for the check attribute to indicate a boolean. For SVM and NB, using the combination of word counts and checks gave the best result. For DT, only the count provided improvement.  

The second added feature was punctuation, i.e. extracting and providing counts for the specific occurrence of "?" and "!". For all classifiers, only checking the occurance of the punctuation provided best results as opposed to the counts of them.  

The third added feature was emoticons, which was hypothesized to significantly improve the classification of positive and negative emotions. While it was not seen to improve from the benchmark on its own as seen in Run 6, when used in combination with the other features, an improvement in accuracy was observed. For all three classifiers, only a check of whether there was a positive or negative emoticon gave the best classification results.  

Fourthly, the addition of elongation as a feature was observed. Elongation is useful in indicating emphasis and again was hypothesized to improve negative and postive classification. Similar as above, while the addition of the feature did not improve from the benchmark, it improved the claissifcation results when working with the first two features. These observations can lead to conclude that different combination of features working together can provide the best results in sentiment classification.    

The above features are helpful indicators of the positive-ness or negative-ness of a text, but not whether it could be objective or neutral. This experiment could be further improved by trying to incorporate features that may help indicate when a text is objective or neutral. While the best results came from the combination of the appropriate features from step 2, it can still be concluded that extra features may be needed for classifying the latter two sentiments.  

Further features could be added that could count for the polarity of a word and create a positive and negative score as from the Polarity Lexicon resource. Larger sets of pre-classified positive and negative words could also be used.  

### Conclusion

In conclusion, certain features are best for classifying positive and negative texts, but make it harder to classify objective and neutral texts. It can also be concluded that while some additional settings when pre-processing texts such as tf-idf, stemming and tokenization delimiters may be useful with document retrieval, the reverse is true for sentiment classification.  
