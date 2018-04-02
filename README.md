# CSI4107 Assignment 2 - Sentiment Analysis
### Authors
Shanzay Amjad - 7394483  
Princejeet Singh Sandhu - 7736952  

### Division

The work division was set out by discussing the possible runs and additional features that could be added to improve final results. Each student worked on creating new features and performing the runs on their machines. Github was used to work in collaboration.  

### Program Functionality

The structure of this program contains the following:  

### Resources 

When developing new features to help train the classifiers, the following resources were used:  
- Porter Stemmer
- Christopher Potts' emoticon regex
- General Inquirer Lexicon of pre-classified positive and negative words

### Step 1
 - bag of words model (words as features). 
 
 **Run 1** - Prof's arff file raw. No stemming, no stop word removal  
 **Run 2** - Our generated arff file with stemming (Porter's) and stop word removal (From Assignment 1).  
 **Run 3** - Prof's file with tf-idf (more wight on rare words), stemming  (IteratedLovinsStemmer), stop word removal (Rainbow).  
 **Run 4** - Our genereated arff file with tf-idf (more wight on rare words), stemming (Porter's stemmer) and stop word removal(From assignment 1).  
 **Run 5** - Our genereated arff file with tf-idf (more wight on rare words), stemming (IteratedLovinsStemmer) and stop word removal(From assignment 1).    
 
 ** For Run 1, had to use only 100 words for decision tree  
 ** Our stop word removal did not result in the same result as stop word removal in weka. We implemented our own algorithm to remove stop words.  

### Step 2
- add extra features and note differences. TODO: features such as emoticons,  pos and neg words, punctuations  
  
 **Run 7** - Our genereated arff file with added attributes: Positive and negative word counts. Since we observed improvements with tf-idf (more wight on rare words), stemming (PorterStemmer) and stop word removal (From assignment 1), we also applied this to the new arff. *Also used lowercase on tokens as a part of normalization...*   
 **Run 8** - Same as above but removed many punctuation delimiters, i.e. kept emoticons and punctuation such as !! ??.  
 **Run 9** - Same as above, but did not use stemmer. Idea: maybe stemmer gets rid of elongated words that may be useful for classification.  
 **Run 10** - Same as above did not use stemmer or tf-idf (also no word count). Idea:  Maybe too much weight is being put on unique words, but these words are not relevant for the sentiment of the document.  
 **Run 11** - Added separate emoticon count features, otherwise same as run 9 (with tf-idf). Idea: rather than just keeping emoticons as part of tokens, i.e. as in the bag of words model, pre-classify the emoticons as either positive or negative emoticons, similar to how we counted positive and negative words.  
 **Run 12** - Removed punctuation again as we were doing before Run 8, so the emoticons will not show as features themselves, but this time we will have just the emoticon count as a feature. Otherwise same as Run 11.    
 **Run 13** - Same as run 10, but added features that check if there is "!" and "?" in the message and no tf-idf. This run did not include the emoticon counts feature.  
 **Run 14** - Same as above, no tf-idf, but including emoticon count feature.  
 **Run 15** - Same as run 14 above, but now instead of positive and negative word counts and emoticon counts, only have booleans for these features. This may help with classifying objective and neutral tweets.  
 **Run 16** - Same as above, but include both counts and booleans for positive and negative words and emoticons.  
 **Run 17** - Same as above, but includes number of "?" and "!"
 **Run 18** - Same as above, but only checks of a feature(except elongation) is present doesn't count them
 **Run 19** - Same as above, but only counts number of times a feature(except elongation) is present doesn't check for them  
 **Run ..** - TODO: Give more weight to the added features that seem to have slightly improved the accuracy...  
 **Run ..** - TODO: Only relevant sentiment bearing words and emoticon counts. Maybe will not do because will make it harder to classify for objective and neutral.  

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
| Run 7         |      51.0     |      31.3     |      46.2     |
| Run 8         |      50.9     |      32.5     |      47.0     |
| Run 9         |      51.1     |      28.7     |      46.0     |
| Run 10        |      50.9     |      41.3     |      46.2     |
| Run 11        |      51.0     |      29.5     |      46.3     |
| Run 12        |      50.6     |      29.8     |      46.8     |
| Run 13        |      50.3     |      49.1     |      48.2     |
| Run 14        |      50.7     |      49.4     |      49.1     |
| Run 15        |      51.3     |      38.3     |      47.5     |
| Run 16        |      51.9     |      39.3     |               |
| Run 17        |      51.0     |      48.0     |      48.5     |
| Run 18        |      51.0     |      48.1     |      48.6     |
| Run 19        |      51.0     |      48.1     |      48.6     |

- maybe stemming makes results worse because when words are elongated they are important to show emphasis of an emotion. Stemming cuts that out
- Further improvements: put heavier weights on emoticon attributes. Also organize between positive and negative emoticons
- better stop word removal
- Seems like tf-idf has negative effect on accuracy for Naive Bayes classifier  
- Harder to find features that indicate if something is objective or neutral. Emoticons / punctuation only help classify if something is positive or negative  
- When increasing term frequency, accuracies went down, this is because rare words were not accounted for.  
- noticed that using counts and booleans together as features give best results.  
- we stopped using tf-idf (caring for rare words) in the later runs because we noticed rare words are useful for describing a doucment, but not necessarily classifying it. Rare words may not give indication on the sentiment of a document.  
- Some conclusions: 
