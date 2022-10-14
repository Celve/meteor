package backend.basic

abstract class Register

data class VirReg(val id: Int) : Register()

data class PhyReg(val id: Int) : Register()

class GloReg : Register()