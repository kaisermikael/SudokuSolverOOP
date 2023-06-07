class Cell(currentVal: Any, coord: Pair<Int,Int>) {
    var possibleVals = ArrayList<String>()
        private set
    var complete = false
        private set
    var currentVal = ""
        private set
    var coord = Pair(0,0)
        private set
    var blockPairVal = ArrayList<String>()

    init {
        this.coord = coord
        this.currentVal = currentVal.toString()
    }

    fun setPossibleVals(newPossibleVals: ArrayList<String>) {
        possibleVals.clear()
        possibleVals.addAll(newPossibleVals)
    }

    fun setCellCurrentVal(newCurrentVal: String) {
        this.currentVal = newCurrentVal
    }

    fun markAsComplete(){
        this.complete = true
    }

    @Override
    override fun toString(): String {
        return currentVal
    }
}