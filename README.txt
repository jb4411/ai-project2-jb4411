Game Theory - ESS Simulation
CSCI 331 - Project 2
Jesse Burdick-Pless jb4411@g.rit.edu

This program simulates individuals in a population. Individuals adopt different strategies when encountering other
individuals. This program tests two strategies, "Hawks" and "Doves". These dictate the outcomes of encounters.


I choose to use Java for this project mainly for two reasons. One reason is that Java is generally a faster language
than Python when considering run time. I wanted the program to be able to simulate thousands of interactions in a
rapid timeframe, and the inherent speed advantage of Java was my primary reason for choosing it over Python. As well,
I am more comfortable with object-oriented programming in Java than in Python, which also caused me to lean towards
picking Java.


The Hawks over time, if they don’t die, build up enough resources through interactions with Doves that the occasional
run in with another Hawk stops having any real likelihood of being lethal (except in extremely unlikely cases). This
happens when the chance of a Hawk encountering another Hawk is low enough that the consequences of Hawk-Hawk
interactions are, on average, offset by the benefits of Hawk-Dove interactions. Thus the Hawk population in most
situations eventually reaches a stable point. What this point is depends on the number of Hawks, the number of Doves,
the cost of a Hawk-Hawk interaction, and the resource value, as well as varying due to the random element of chance,
especially early in the simulation.

From a game theory or intelligent systems perspective, choosing the Dove strategy is generally a safe strategy for
surviving. Unlike Hawks, Doves cannot be killed during an encounter with another individual. However, generally Hawks
can gather more resources than Doves assuming they live long enough. The Hawk strategy is a high-risk, high-reward
strategy. If the goal is to maximize the resources gathered, the Hawk strategy is the way to go. However, unlike with
the Dove strategy, an individual using the Hawk strategy is always at risk of dying as long as there is at least one
other living Hawk. Because of this, the Dove strategy may come out on top in terms of average resources gathered across
multiple simulations. However this will be dependent on the number of interactions in each simulation, the number of
Hawks, the number of Doves, the resource value, the cost of a Hawk-Hawk interaction, and the randomness of which
individuals are chosen for encounters.

The number of moving parts here is really astounding for how simple the simulation seems on the surface. The ratio of
Hawks to Doves, the ratio of the value of each resource to the cost of a Hawk-Hawk interaction, the number of remaining
Hawks, all play a complex role in determining what the number of living Hawks converges to. In addition, chance plays a
large role in the beginning as at that point Hawks do not yet have a resource buffer to save them if they encounter
another Hawk. Later with a buffer built up from interactions with Doves, chance becomes rather unimportant. As a result,
what the number of living Hawks converges to varies even between simulations with the same parameters because chance
matters so much at the beginning in determining how many Hawks die.


H = Hawk
D = Dove
r = resource value
c = cost of a Hawk-Hawk interaction


ED(D) = r // 2
ED(H) = 0

We can see that
r // 2  >  0,  for all  r  >  1  →  ED(D)  >  ED(H)

Therefore, as long as the resource value is greater than 1, the Dove strategy is an Evolutionary Stable Strategy.

As well, on average:
EH(H) = (r / 2) - c
EH(D) = r

r  >  (r / 2) - c,  for  r  >=  0,  with  c  >  0.  →  EH(D)  >  EH(H)

From this we can see that even if the resource value is 1 or 0, the Dove strategy is still an Evolutionary Stable
Strategy if the cost of a Hawk-Hawk interaction is greater than 0.

As long as the resource value is greater than 0 (and the cost of a Hawk-Hawk interaction is not a sufficiently negative
number), the Hawk strategy is not an Evolutionary Stable Strategy.

If the resource value is 0, the Hawk strategy is still not an Evolutionary Stable Strategy if the cost of a Hawk-Hawk
interaction is greater than 0.

Thus, in a reasonable scenario where the resource value is greater than 0 and the cost of a Hawk-Hawk interaction is
also greater than 0, the Dove strategy is an Evolutionary Stable Strategy. The Hawk strategy is an Evolutionary Stable
Strategy only in irregular scenarios where you have a negative resource value, a negative Hawk-Hawk interaction cost,
or both.



How to run it:

The main Search class is run as:
    $java project02 popSize [percentHawks] [resourceAmt] [costHawk-Hawk]
        popSize: The total number of individuals in your population (required)
        [percentHawks]: The percentage of the population employing the Hawk strategy (optional, defaults to 20%)
        [resourceAmt]: The value of each resource (optional, defaults to 50)
        [costHawk-Hawk]: The cost of a Hawk-Hawk interaction (optional, defaults to 100)



Interacting with the program:

When the program is run, the user is shown the following main menu:
===============MENU=============
1 ) Starting Stats
2 ) Display Individuals and Points
3 ) Display Sorted
4 ) Have 1000 interactions
5 ) Have 10000 interactions
6 ) Have N interactions
7 ) Step through interactions "Stop" to return to menu
8 ) Quit
================================

Entering "1" displays the starting statistics for the simulation.

Entering "2" displays all individuals in the population, the strategy each is using if they are alive or "DEAD"
if they have died, the accumulated resources for each individual, and the number of living individuals remaining.

Entering "3" displays all individuals in the population, the strategy each is using if they are alive or "DEAD"
if they have died, and the accumulated resources for each individual. Additionally, the output is sorted
so that the individuals are ordered from most to least resources.

Entering "4" or "5" runs 1000 and 10000 interactions respectively.

Entering "6" prompts the user to enter a number, and then runs that many interactions.

For items "4", "5" and "6", each encounter is displayed, regardless of the number of interactions being performed.
If all members of the population are dead, or there is only one living individual left, the simulation cannot continue.
If this happens a message is printed informing the user of what happened, and stating that the simulation is complete.
The program then returns to the main menu. Entering "4", "5" or "6" in this state will simply display the simulation
complete message again.

Entering "7" advances the simulation one interaction at a time. When the user hits <Enter>, another interaction is
displayed. This continues until the simulation reaches a state where it cannot continue, or the user enters "Stop".
As with items "4", "5" and "6", if the simulation cannot continue, a message is printed informing the user of what
happened, stating that the simulation is complete. Entering "7" in this state will simply display the simulation
complete message again as with items "4", "5" and "6".

After any one of these first 7 items is run, the program displays the menu again.

Entering "8" quits the simulation.



Simulation details:

Initially all individuals have a resource level of 0.

Interactions between two individuals are simulated as follows:
    Two living individuals are randomly selected.

    These individuals "compete" for a resource using the guidelines below.

    There are three types of interactions, Hawk-Hawk, Dove-Dove, and Hawk-Dove (Hawk-Dove and Dove-Hawk are equivalent).
        Hawk-Hawk: When a Hawk encounters another Hawk, the Hawk that was selected first receives the resource
                   and both Hawks lose resources equal to the Hawk-Hawk interaction cost. If a Hawk's resource
                   level drops below 0, the Hawk dies. It is possible for both Hawks to die during an encounter.
                   Dead Hawks can no longer be chosen for encounters.

        Dove-Dove: When a Dove encounters another Dove, they to split the resource equally. If the resource amount
                   cannot be divided evenly, each Dove receives the truncated result of the division.

        Hawk-Dove: When a Hawk encounters a Dove, the Hawk takes the entire resource and the Dove gets nothing.


    For each encounter the following is displayed:
        The encounter number
        Each individual and their respective strategy (Dove or Hawk)
        The type of interaction (Hawk-Hawk, Dove-Dove, or Hawk-Dove).
        The resource change amounts for each individual.
        During a Hawk-Hawk encounter, if one or both Hawks die, a message stating this is displayed.
        Finally, each individual and their respective new resource levels are displayed.