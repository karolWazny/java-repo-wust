function engineName(){
    return "Wireworld";
}

var EMPTY = 0;
var CONDUCTOR = 1;
var HEAD = 2;
var TAIL = 3;

function evaluateState(neighbourhood){
    if(neighbourhood[4] === EMPTY){
        return EMPTY;
    } else if (neighbourhood[4] === HEAD) {
        return TAIL;
    } else if (neighbourhood[4] === TAIL) {
        return CONDUCTOR;
    } else if (neighbourhood[4] === CONDUCTOR) {
        var neighbouringHeads = 0;
        for(var i = 0; i < neighbourhood.length; i++) {
            if(neighbourhood[i] === HEAD){
                neighbouringHeads = neighbouringHeads + 1;
            }
        }
        if(neighbouringHeads < 3 && neighbouringHeads > 0){
            return HEAD;
        } else {
            return CONDUCTOR;
        }
    }
    return EMPTY;
}

function cycleThroughStates(state){
    return (state + 1) % 4;
}

function colorForState(state){
    if(state === 1){
        return "0xDDBB00";
    } else if (state === 2){
        return "0xFF0000";
    } else if (state === 3) {
        return "0x0000FF";
    } else {
        return "0x000000";
    }

}