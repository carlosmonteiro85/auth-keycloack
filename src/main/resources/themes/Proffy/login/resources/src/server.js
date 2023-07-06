

const express = require('express')
const server = express()

server.use(express.static("public"))
.get("/", (req,res) => {
    return res.sendfile(__dirname + "/views/index.html")
} )

.get("/Study", (rep,res) => {
    return res.sendfile(__dirname + "/views/study.html")
})

.get("/give-classes", (rep,res) => {
    return res.sendfile(__dirname + "/views/give-classes.html")
})

.listen(5500)