function engineName(){
    return "The Game of Life";
}

function evaluateState(neighbourhood){
    var living = countAliveCells(neighbourhood);
    if(neighbourhood[4] === 1){
        if(living > 4 || living < 3){
            return 0;
        } else {
            return 1;
        }
    } else {
        if(living === 3){
            return 1;
        } else {
            return 0;
        }
    }
}

function cycleThroughStates(state){
    return (state + 1) % 2;
}

function countAliveCells(neighbourhood){
    var sum = 0;
    for(var i =0; i < neighbourhood.length; i++){
        if(neighbourhood[i] === 1){
            sum = sum + 1
        }
    }
    return sum;
}

function colorForState(state){
    if(state === 1){
        return "0x000000";
    } else {
        return "0xFFFFFF";
    }

}