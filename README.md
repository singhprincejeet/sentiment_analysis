# CSI4107 Assignment 2 - Sentiment Analysis
### Authors
Shanzay Amjad - 7394483  
Princejeet Singh Sandhu - 

### Step 1
 - bag of words model (words as features). 
 
 **Run 1** - Prof's arff file raw. No stemming, no stop word removal  
 **Run 2** - Our generated arff file with stemming (Porter's) and stop word removal (From Assignment 1).  
 **Run 3** - Prof's file with tf-idf (more wight on rare words), stemming  (IteratedLovinsStemmer), stop word removal (Rainbow).  
 **Run 4** - Our genereated arff file with tf-idf (more wight on rare words), stemming (Porter's stemmer) and stop word removal(From assignment 1).  
 **Run 5** - Our genereated arff file with tf-idf (more wight on rare words), stemming (IteratedLovinsStemmer) and stop word removal(From assignment 1).  
 **Run 6** - Our genereated arff file with Attribute Selection. (Check infogain of each word and use words that pass a certain threshold as features)  
 
 ** For Run 1, had to use only 100 words for decision tree  
 ** Our stop word removal did not result in the same result as stop word removal in weka. We implemented our own algorithm to remove stop words.  

### Step 2
- add extra features and note differences. TODO: features such as emoticons,  pos and neg words, punctuations  
  
 **Run 7** - Our genereated arff file with added attributes: Positive and negative word counts. Since we observed improvements with tf-idf (more wight on rare words), stemming (PorterStemmer) and stop word removal (From assignment 1), we also applied this to the new arff. *Also used lowercase on tokens as a part of normalization...*    

- We noticed this did not improve results, in fact made it worse because the many positive words were often used in a negative context and vice-versa.  
  
### Results / Discussion

 **Need to report accuracy, confusion matrices, precision, recall and f-measures**  
 **Table 1 - Summary of Results (% Accuracy Only)**

|               |      SVM      |       NB      |       DT      |
| ------------- | ------------- | ------------- | ------------- |
| Run 1         |      52.6     |      45.2     |      43.2     |
| Run 2         |      50.3     |      45.5     |      45.7     |
| Run 3         |               |      34.5     |      44.8     |
| Run 4         |      50.3     |      34.5     |      45.0     |
| Run 5         |      50.3     |      34.5     |      45.0     |
| Run 6         |               |               |               |
| Run 7         |      51.0     |      31.3     |      46.2     |

