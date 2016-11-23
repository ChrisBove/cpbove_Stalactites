This Stalactites implementation follows the definition provided by this Wikipedia page:

https://en.wikipedia.org/wiki/Stalactites_(solitaire)

The rubric, which was posted a few hours ago, has moves that are not valid according to the Wikipedia page,
namely "Reserve to Tableau" and "Tableau to Empty Tableau". These moves are not considered in my implementation
since they do not follow the rules given in the Wikipedia article.

Additionally, a cell to cell move is not considered, as it cannot alter gameplay outcome, and the article states that 
"...once a card is placed on the reserve, it must be built on a foundation." Having this move would contradict
the idea that a card is locked in the cell unless it can be freed by being placed directly on the foundation.

I've done my best to relink the package to work under the "git" directory, but please let me know if you have trouble.

Thank you - Christopher Bove