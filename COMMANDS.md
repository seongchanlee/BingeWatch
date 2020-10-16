# Commands for BingeWatch

## addShow
Adds a show for the bot to keep track of.
```
-bw addShow [show name in quotation]
```
Sample: ```-bw addShow "One Piece"```

## removeShow
Removes given show.
```
-bw removeShow [show name in quotation]
```
Sample: ```-bw removeShow "One Piece"```

## updateEpisode
Updates the episode number for given show.
```
-bw updateEpisode [show name in quotation] [episode number]
```
Sample: ```-bw updateEpisode "One Piece" 25```

## addFillers
Adds filler episode range so bot is aware of which episode to skip.
```
-bw addFillers [show name in quotation] [start episode number] [end episode number]
```
Sample: ```-bw addFillers "One Piece" 8 20```

## nextEpisode
Returns which episode to watch next for the given show.
```
-bw nextEpisode [show name in quotation]
```
Sample: ```-bw nextEpisode "One Piece"```

## getList
Returns list of shows bot is tracking as well as their respective current episode numbers.
```
-bw getList
```