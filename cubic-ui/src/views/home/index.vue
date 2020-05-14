<template>

    <el-container class="wrapper">
        <el-scrollbar class="container-left" wrap-class="container-left-wrap" :class="{'slide-hide': isCollapse, 'slide-in-left': menuShow}">
            <div class="logo">
                <!--图片logo-->
                <img alt="element-logo"
                src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+Cjxzdmcgd2lkdGg9IjE0NnB4IiBoZWlnaHQ9IjM4cHgiIHZpZXdCb3g9IjAgMCAxNDYgMzgiIHZlcnNpb249IjEuMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayI+CiAgICA8IS0tIEdlbmVyYXRvcjogU2tldGNoIDQwICgzMzc2MikgLSBodHRwOi8vd3d3LmJvaGVtaWFuY29kaW5nLmNvbS9za2V0Y2ggLS0+CiAgICA8dGl0bGU+U2hhcGUgQ29weTwvdGl0bGU+CiAgICA8ZGVzYz5DcmVhdGVkIHdpdGggU2tldGNoLjwvZGVzYz4KICAgIDxkZWZzPjwvZGVmcz4KICAgIDxnIGlkPSJ2Mi4yLjAiIHN0cm9rZT0ibm9uZSIgc3Ryb2tlLXdpZHRoPSIxIiBmaWxsPSJub25lIiBmaWxsLXJ1bGU9ImV2ZW5vZGQiPgogICAgICAgIDxnIGlkPSLpppbpobUt6buY6K6k5pWI5p6cLWNvcHktMiIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoLTcwLjAwMDAwMCwgLTE5LjAwMDAwMCkiIGZpbGw9IiNGRkZGRkYiPgogICAgICAgICAgICA8cGF0aCBkPSJNMjEyLjEzNTQ0MSw0NS4xNTc4MDc3IEMyMTIuMTM1NDQxLDQ1LjE1NzgwNzcgMjEyLjQyMDIzNyw0NS4xNTA1MTA1IDIxMi45NjA5MzcsNDUuMTU3ODA3NyBDMjEzLjUwMTYzNyw0NS4xNjUxMDQ5IDIxMy42NDg4NTEsNDUuNTcwODU1NiAyMTMuNjQ4ODUxLDQ1LjU3MDg1NTYgQzIxMy42NDg4NTEsNDUuNTcwODU1NiAyMTQuNzMzODI4LDQ3LjU2NjU2NTcgMjE1LjAyNDY3Nyw0OC4wNDkxNDM0IEMyMTUuMjgzNjA4LDQ4LjQ3ODcxMzMgMjE0Ljk3MzIyMSw0OC40NzA4NjU0IDIxNC45MDE4MTYsNDguNDYzODQzNiBDMjE0LjkwMjUwNCw0OC40NjI4Nzk4IDIxNC44ODcwOTUsNDguNDYyMTkxNCAyMTQuODg3MDk1LDQ4LjQ2MjE5MTQgQzIxNC44ODcwOTUsNDguNDYyMTkxNCAyMTQuODkzMDExLDQ4LjQ2MzAxNzUgMjE0LjkwMTgxNiw0OC40NjM4NDM2IEMyMTQuODk4NjUyLDQ4LjQ2ODI0OTUgMjE0LjU5NDczMSw0OC40NzgzMDAzIDIxMS40NDc1MjgsNDguNDYyMTkxNCBDMjA3LjA1ODY0MSw0Ny44NjY1NzYyIDIwNi45MDczLDQzLjkxODY2MzggMjA2LjkwNzMsNDMuOTE4NjYzOCBMMjA2LjkwNzMsMzYuODk2ODQ4MyBMMjA0LjQzMDgxMiwzNi44OTY4NDgzIEwyMDQuNDMwODEyLDM0LjI4MDg3NzkgQzIwNC40MzA4MTIsMzMuOTI0Mjc5OCAyMDQuODQzNTYsMzMuODY3ODI5OSAyMDQuODQzNTYsMzMuODY3ODI5OSBMMjA2LjkwNzMsMzMuODY3ODI5OSBMMjA2LjkwNzMsMzEuMjUxODU5NCBDMjA2LjkwNzMsMzAuODI1MTgwOSAyMDcuMzIwMDQ4LDMwLjcwMTEyODggMjA3LjMyMDA0OCwzMC43MDExMjg4IEMyMDcuMzIwMDQ4LDMwLjcwMTEyODggMjA4Ljc3MjY0NiwzMC4yODU4Nzc5IDIwOS42MTc4MTYsMzAuMDQ0MTA3MiBDMjA5Ljk2NDY2MiwyOS45NDQ5NzU2IDIxMC4wNzE3MDEsMzAuMTUwMzk4MiAyMTAuMDcxNzAxLDMwLjE1MDM5ODIgQzIxMC4wNzE3MDEsMzAuMTUwMzk4MiAyMTAuMjA5Mjg0LDMwLjA3NzQyNjQgMjEwLjIwOTI4NCwzMC4zOTI3MTk2IEwyMTAuMjA5Mjg0LDM0LjAwNTUxMjUgTDIxMy43ODY0MzMsMzQuMDA1NTEyNSBDMjE0LjEzNDY1NSwzNC4wMDU1MTI1IDIxNC4xOTkxODEsMzQuNDE4NTYwNSAyMTQuMTk5MTgxLDM0LjQxODU2MDUgTDIxNC4xOTkxODEsMzcuMDM0NTMxIEwyMTAuMjA5Mjg0LDM3LjAzNDUzMSBMMjEwLjIwOTI4NCw0My4wOTI1Njc4IEMyMTAuMjA5Mjg0LDQ0Ljg1MzM5MTMgMjEyLjEzNTQ0MSw0NS4xNTc4MDc3IDIxMi4xMzU0NDEsNDUuMTU3ODA3NyBMMjEyLjEzNTQ0MSw0NS4xNTc4MDc3IFogTTIwMS41NDE1NzYsNDguMTg2ODI2MSBMMTk5LjM0MDI1Myw0OC4xODY4MjYxIEMxOTguOTA4MzgxLDQ4LjE4NjgyNjEgMTk4LjkyNzUwNSw0Ny42MzYwOTU1IDE5OC45Mjc1MDUsNDcuNjM2MDk1NSBMMTk4LjkyNzUwNSwzNy40NDc1Nzg5IEMxOTguOTI3NTA1LDM2LjgyODY5NTQgMTk4LjEwMjAwOSwzNi42MjE0ODMgMTk4LjEwMjAwOSwzNi42MjE0ODMgTDE5My41NjE3ODEsMzYuNjIxNDgzIEMxOTIuODg0NDYyLDM2LjYyMTQ4MyAxOTIuODczODY4LDM3LjQ0NzU3ODkgMTkyLjg3Mzg2OCwzNy40NDc1Nzg5IEwxOTIuODczODY4LDQ3Ljc3Mzc3ODEgQzE5Mi44NzM4NjgsNDguMTc4NzAyOCAxOTIuNDYxMTIsNDguMTg2ODI2MSAxOTIuNDYxMTIsNDguMTg2ODI2MSBMMTkwLjI1OTc5Nyw0OC4xODY4MjYxIEMxODkuNzc1NzgyLDQ4LjE4NjgyNjEgMTg5Ljg0NzA0OSw0Ny42MzYwOTU1IDE4OS44NDcwNDksNDcuNjM2MDk1NSBMMTg5Ljg0NzA0OSwzNC44MzE2MDg1IEMxODkuODQ3MDQ5LDMzLjgwMzExOSAxOTEuMDg1MjkzLDMzLjU5MjQ2NDYgMTkxLjA4NTI5MywzMy41OTI0NjQ2IEwyMDAuNzE2MDgsMzMuNTkyNDY0NiBDMjAxLjY3Nzc4MywzMy41OTI0NjQ2IDIwMS45NTQzMjQsMzQuODMxNjA4NSAyMDEuOTU0MzI0LDM0LjgzMTYwODUgTDIwMS45NTQzMjQsNDcuNDk4NDEyOCBDMjAxLjk1NDMyNCw0OC4yMTg2MzA4IDIwMS41NDE1NzYsNDguMTg2ODI2MSAyMDEuNTQxNTc2LDQ4LjE4NjgyNjEgTDIwMS41NDE1NzYsNDguMTg2ODI2MSBaIE0xODUuODU3MTUyLDQyLjQwNDE1NDYgTDE3Ny4zMjcwMjcsNDIuNDA0MTU0NiBMMTc3LjMyNzAyNyw0NC40NjkzOTQ0IEMxNzcuMzI3MDI3LDQ1LjIxMzU2OTEgMTc4LjI5MDEwNiw0NS40MzMxNzMgMTc4LjI5MDEwNiw0NS40MzMxNzMgTDE4NS4xNjkyMzksNDUuNDMzMTczIEMxODUuNjU4NzU4LDQ1LjQzMzE3MyAxODUuOTk0NzM1LDQ1Ljg0NjIyMSAxODUuOTk0NzM1LDQ1Ljg0NjIyMSBDMTg1Ljk5NDczNSw0NS44NDYyMjEgMTg2LjU5NTAwOCw0Ny4wOTUxNDAzIDE4Ni44MjAyMzEsNDcuNjM2MDk1NSBDMTg3LjA0NTQ1NCw0OC4xNzcwNTA2IDE4Ni40MDc0ODMsNDguMTg2ODI2MSAxODYuNDA3NDgzLDQ4LjE4NjgyNjEgTDE3Ni4wODg3ODMsNDguMTg2ODI2MSBDMTc0Ljg0NjEzNiw0OC4xODY4MjYxIDE3NC41NzUzNzQsNDYuNjcyMzE2OSAxNzQuNTc1Mzc0LDQ2LjY3MjMxNjkgTDE3NC41NzUzNzQsMzQuOTY5MjkxMSBDMTc0LjU3NTM3NCwzNC4yOTYyOTgzIDE3NS41Mzg0NTIsMzQuMDA1NTEyNSAxNzUuNTM4NDUyLDM0LjAwNTUxMjUgTDE4NS44NTcxNTIsMzQuMDA1NTEyNSBDMTg2Ljg5NDExMywzNC4wMDU1MTI1IDE4Ny4wOTUzOTYsMzUuMjQ0NjU2NCAxODcuMDk1Mzk2LDM1LjI0NDY1NjQgTDE4Ny4wOTUzOTYsNDEuMTY1MDEwNyBDMTg3LjA5NTM5Niw0Mi4xNjM4OTgzIDE4NS44NTcxNTIsNDIuNDA0MTU0NiAxODUuODU3MTUyLDQyLjQwNDE1NDYgTDE4NS44NTcxNTIsNDIuNDA0MTU0NiBaIE0xODQuMzQzNzQzLDM3LjQ0NzU3ODkgQzE4NC4zNDM3NDMsMzcuNDQ3NTc4OSAxODQuMjU3MDY2LDM2Ljc1OTE2NTcgMTgzLjY1NTgzLDM2Ljc1OTE2NTcgTDE3OC4wMTQ5NCwzNi43NTkxNjU3IEMxNzguMDE0OTQsMzYuNzU5MTY1NyAxNzcuMzI3MDI3LDM2Ljk0Mzc5ODEgMTc3LjMyNzAyNywzNy40NDc1Nzg5IEwxNzcuMzI3MDI3LDM4Ljk2MjA4ODIgQzE3Ny4zMjcwMjcsMzguOTYyMDg4MiAxNzcuMzQ2ODM5LDM5LjY1MDUwMTQgMTc4LjAxNDk0LDM5LjY1MDUwMTQgTDE4My42NTU4MywzOS42NTA1MDE0IEMxODMuNjU1ODMsMzkuNjUwNTAxNCAxODQuMzQzNzQzLDM5LjQ4MDQ2MzQgMTg0LjM0Mzc0MywzOC44MjQ0MDU1IEwxODQuMzQzNzQzLDM3LjQ0NzU3ODkgTDE4NC4zNDM3NDMsMzcuNDQ3NTc4OSBaIE0xNzEuNDEwOTcyLDQ4LjE4NjgyNjEgTDE2OS4yMDk2NSw0OC4xODY4MjYxIEMxNjguOTE5MjEzLDQ4LjE4NjgyNjEgMTY4Ljc5NjkwMiw0Ny43NzM3NzgxIDE2OC43OTY5MDIsNDcuNzczNzc4MSBMMTY4Ljc5NjkwMiwzNy40NDc1Nzg5IEMxNjguNzk2OTAyLDM2LjgwNzIxNjkgMTY3Ljk3MTQwNiwzNi42MjE0ODMgMTY3Ljk3MTQwNiwzNi42MjE0ODMgTDE2NS45MDc2NjYsMzYuNjIxNDgzIEMxNjUuMzMxODgyLDM2LjYyMTQ4MyAxNjUuMjE5NzUyLDM3LjQ0NzU3ODkgMTY1LjIxOTc1MiwzNy40NDc1Nzg5IEwxNjUuMjE5NzUyLDQ3Ljc3Mzc3ODEgQzE2NS4yMTk3NTIsNDguMTE2MzMyNiAxNjQuODA3MDA0LDQ4LjE4NjgyNjEgMTY0LjgwNzAwNCw0OC4xODY4MjYxIEwxNjIuNjA1NjgyLDQ4LjE4NjgyNjEgQzE2Mi4yOTk1Niw0OC4xODY4MjYxIDE2Mi4xOTI5MzQsNDcuNzczNzc4MSAxNjIuMTkyOTM0LDQ3Ljc3Mzc3ODEgTDE2Mi4xOTI5MzQsMzcuNDQ3NTc4OSBDMTYyLjE5MjkzNCwzNi43OTI4OTc5IDE2MS4zNjc0MzgsMzYuNjIxNDgzIDE2MS4zNjc0MzgsMzYuNjIxNDgzIEwxNTkuMzAzNjk4LDM2LjYyMTQ4MyBDMTU4LjY3Mzk4MiwzNi42MjE0ODMgMTU4LjYxNTc4NCwzNy40NDc1Nzg5IDE1OC42MTU3ODQsMzcuNDQ3NTc4OSBMMTU4LjYxNTc4NCw0Ny43NzM3NzgxIEMxNTguNjE1Nzg0LDQ4LjIxNTczOTUgMTU4LjIwMzAzNiw0OC4xODY4MjYxIDE1OC4yMDMwMzYsNDguMTg2ODI2MSBMMTU2LjAwMTcxNCw0OC4xODY4MjYxIEMxNTUuNTg5Mzc4LDQ4LjE4NjgyNjEgMTU1LjU4ODk2Niw0Ny43NzM3NzgxIDE1NS41ODg5NjYsNDcuNzczNzc4MSBMMTU1LjU4ODk2NiwzNC42OTM5MjU4IEMxNTUuNTg4OTY2LDMzLjkzNjI1ODIgMTU2LjY4OTYyNywzMy41OTI0NjQ2IDE1Ni42ODk2MjcsMzMuNTkyNDY0NiBMMTcwLjQ0Nzg5NCwzMy41OTI0NjQ2IEMxNzEuNTUyMjcsMzMuNTkyNDY0NiAxNzEuODIzNzIsMzQuOTY5MjkxMSAxNzEuODIzNzIsMzQuOTY5MjkxMSBMMTcxLjgyMzcyLDQ3Ljc3Mzc3ODEgQzE3MS44MjM3Miw0OC4yNDkzMzQgMTcxLjQxMDk3Miw0OC4xODY4MjYxIDE3MS40MTA5NzIsNDguMTg2ODI2MSBMMTcxLjQxMDk3Miw0OC4xODY4MjYxIFogTTE1MS40NjE0ODYsNDIuNDA0MTU0NiBMMTQyLjkzMTM2LDQyLjQwNDE1NDYgTDE0Mi45MzEzNiw0NC40NjkzOTQ0IEMxNDIuOTMxMzYsNDUuMjEzNTY5MSAxNDMuODk0NDM5LDQ1LjQzMzE3MyAxNDMuODk0NDM5LDQ1LjQzMzE3MyBMMTUwLjc3MzU3Miw0NS40MzMxNzMgQzE1MS4yNjMwOTIsNDUuNDMzMTczIDE1MS41OTkwNjgsNDUuODQ2MjIxIDE1MS41OTkwNjgsNDUuODQ2MjIxIEMxNTEuNTk5MDY4LDQ1Ljg0NjIyMSAxNTIuMTk5MjA0LDQ3LjA5NTE0MDMgMTUyLjQyNDU2NCw0Ny42MzYwOTU1IEMxNTIuNjQ5Nzg3LDQ4LjE3NzA1MDYgMTUyLjAxMTgxNiw0OC4xODY4MjYxIDE1Mi4wMTE4MTYsNDguMTg2ODI2MSBMMTQxLjY5MzExNyw0OC4xODY4MjYxIEMxNDAuNDUwNDcsNDguMTg2ODI2MSAxNDAuMTc5NzA3LDQ2LjY3MjMxNjkgMTQwLjE3OTcwNyw0Ni42NzIzMTY5IEwxNDAuMTc5NzA3LDM0Ljk2OTI5MTEgQzE0MC4xNzk3MDcsMzQuMjk2Mjk4MyAxNDEuMTQyNzg2LDM0LjAwNTUxMjUgMTQxLjE0Mjc4NiwzNC4wMDU1MTI1IEwxNTEuNDYxNDg2LDM0LjAwNTUxMjUgQzE1Mi40OTg0NDYsMzQuMDA1NTEyNSAxNTIuNjk5NzMsMzUuMjQ0NjU2NCAxNTIuNjk5NzMsMzUuMjQ0NjU2NCBMMTUyLjY5OTczLDQxLjE2NTAxMDcgQzE1Mi42OTk3Myw0Mi4xNjM4OTgzIDE1MS40NjE0ODYsNDIuNDA0MTU0NiAxNTEuNDYxNDg2LDQyLjQwNDE1NDYgTDE1MS40NjE0ODYsNDIuNDA0MTU0NiBaIE0xNDkuOTQ4MDc2LDM3LjQ0NzU3ODkgQzE0OS45NDgwNzYsMzcuNDQ3NTc4OSAxNDkuODYxMzk5LDM2Ljc1OTE2NTcgMTQ5LjI2MDE2MywzNi43NTkxNjU3IEwxNDMuNjE5Mjc0LDM2Ljc1OTE2NTcgQzE0My42MTkyNzQsMzYuNzU5MTY1NyAxNDIuOTMxMzYsMzYuOTQzNzk4MSAxNDIuOTMxMzYsMzcuNDQ3NTc4OSBMMTQyLjkzMTM2LDM4Ljk2MjA4ODIgQzE0Mi45MzEzNiwzOC45NjIwODgyIDE0Mi45NTExNzIsMzkuNjUwNTAxNCAxNDMuNjE5Mjc0LDM5LjY1MDUwMTQgTDE0OS4yNjAxNjMsMzkuNjUwNTAxNCBDMTQ5LjI2MDE2MywzOS42NTA1MDE0IDE0OS45NDgwNzYsMzkuNDgwNDYzNCAxNDkuOTQ4MDc2LDM4LjgyNDQwNTUgTDE0OS45NDgwNzYsMzcuNDQ3NTc4OSBMMTQ5Ljk0ODA3NiwzNy40NDc1Nzg5IFogTTEzOC4xMTU5NjcsNDguMTg2ODI2MSBMMTMyLjg4NzgyNiw0OC4xODY4MjYxIEMxMzIuODg3ODI2LDQ4LjE4NjgyNjEgMTI5LjA2MTkyNyw0OC4wMDYxODY1IDEyOC43NjAzNDYsNDQuMTk0MDI5MSBDMTI4Ljc0ODIzOSw0MC4yNzkwMjI4IDEyOC43NjAzNDYsMjguMDg1MTU4MyAxMjguNzYwMzQ2LDI4LjA4NTE1ODMgTDEzMS4yMzY4MzQsMjguMDg1MTU4MyBDMTMxLjIzNjgzNCwyOC4wODUxNTgzIDEzMi4wNjIzMywyOC4wNzAxNTA5IDEzMi4wNjIzMywyOC45MTEyNTQzIEwxMzIuMDYyMzMsNDMuMzY3OTMzMSBDMTMyLjA2MjMzLDQzLjM2NzkzMzEgMTMyLjM1MDAxNSw0NC41NjM4NDQ3IDEzMy45ODg0ODcsNDUuMDIwMTI1IEMxMzUuODM5OCw0NS4wMTA3NjI2IDEzNi4xODk4MSw0NS4wMjAxMjUgMTM2LjE4OTgxLDQ1LjAyMDEyNSBDMTM2LjE4OTgxLDQ1LjAyMDEyNSAxMzYuOTA3MzAzLDQ0Ljc5MDE5NSAxMzcuNDI4MDU0LDQ1LjcwODUzODMgQzEzNy45NDg4MDQsNDYuNjI2ODgxNiAxMzguNTI4NzE1LDQ3LjYzNjA5NTUgMTM4LjUyODcxNSw0Ny42MzYwOTU1IEMxMzguNTI4NzE1LDQ3LjYzNjA5NTUgMTM4LjYxMTU0LDQ4LjE4NjgyNjEgMTM4LjExNTk2Nyw0OC4xODY4MjYxIEwxMzguMTE1OTY3LDQ4LjE4NjgyNjEgWiBNMTExLjU2MjUxMyw0Ny4zNjA3MzAyIEwxMTEuNTYyNTEzLDI5LjE4NjYxOTYgQzExMS41NjI1MTMsMjguMzgwNzYzIDExMi4yNTA0MjYsMjguMjIyODQxIDExMi4yNTA0MjYsMjguMjIyODQxIEwxMjUuMTgzMTk3LDI4LjIyMjg0MSBDMTI1LjkwNjQ2OSwyOC4yMjI4NDEgMTI1LjU5NTk0NSwyOS4wNDg5MzY5IDEyNS41OTU5NDUsMjkuMDQ4OTM2OSBDMTI1LjU5NTk0NSwyOS4wNDg5MzY5IDEyNS4xOTI4MjcsMzAuMTY2MDk0IDEyNC45MDgwMzEsMzAuNzAxMTI4OCBDMTI0LjYyMzIzNSwzMS4yMzYxNjM2IDEyNC4wODI1MzUsMzEuMjUxODU5NCAxMjQuMDgyNTM1LDMxLjI1MTg1OTQgTDExNS42ODk5OTMsMzEuMjUxODU5NCBDMTE0LjkwODUyMywzMS4yNTE4NTk0IDExNC44NjQ0OTcsMzEuOTQwMjcyNyAxMTQuODY0NDk3LDMxLjk0MDI3MjcgTDExNC44NjQ0OTcsMzYuNDgzODAwMyBMMTI0LjM1NzcwMSwzNi40ODM4MDAzIEMxMjQuOTE0NjM1LDM2LjQ4MzgwMDMgMTI0LjYzMjg2NiwzNy4xNzIyMTM2IDEyNC42MzI4NjYsMzcuMTcyMjEzNiBDMTI0LjYzMjg2NiwzNy4xNzIyMTM2IDEyMy45MjE4MzksMzguNjg4OTI1OCAxMjMuNjY5Nzg3LDM5LjA5OTc3MDggQzEyMy40MTc3MzYsMzkuNTEwNjE1OSAxMjIuODQ0MjkxLDM5LjUxMjgxODggMTIyLjg0NDI5MSwzOS41MTI4MTg4IEwxMTQuODY0NDk3LDM5LjUxMjgxODggTDExNC44NjQ0OTcsNDQuMzMxNzExNyBDMTE0Ljg2NDQ5Nyw0NC45ODg0NTggMTE1LjU1MjQxLDQ1LjE1NzgwNzcgMTE1LjU1MjQxLDQ1LjE1NzgwNzcgTDEyNC4zNTc3MDEsNDUuMTU3ODA3NyBDMTI0Ljk5NjIyMiw0NS4xNTc4MDc3IDEyNS4xODMxOTcsNDUuNTcwODU1NiAxMjUuMTgzMTk3LDQ1LjU3MDg1NTYgQzEyNS4xODMxOTcsNDUuNTcwODU1NiAxMjYuMDU1MTk2LDQ2LjkzMjM5OTQgMTI2LjQyMTQ0MSw0Ny40OTg0MTI4IEMxMjYuNzg3ODIzLDQ4LjA2NDQyNjIgMTI2LjI4Mzg1OCw0OC4xODY4MjYxIDEyNi4yODM4NTgsNDguMTg2ODI2MSBMMTEyLjM4ODAwOSw0OC4xODY4MjYxIEMxMTEuODE0MTUxLDQ4LjE4NjgyNjEgMTExLjU2MjUxMyw0Ny4zNjA3MzAyIDExMS41NjI1MTMsNDcuMzYwNzMwMiBaIE0xMDMuNDE2NTAyLDQ2LjIxNzU1MTEgQzEwMy40MDcwMDgsNDcuNzk0NTY4MiAxMDIuNTg3ODQxLDQ4LjE0NjM0NzQgMTAyLjU4Nzg0MSw0OC4xNDYzNDc0IEMxMDIuNTg3ODQxLDQ4LjE0NjM0NzQgODguNDUyMDQ3OCw1Ni4zMTQ1MDg3IDg3LjUzMjk5NTYsNTYuODI2Mjc1MSBDODYuNjIyMzM2LDU3LjIxNzE1NjEgODYuMDEzNjcwMyw1Ni44MjYyNzUxIDg2LjAxMzY3MDMsNTYuODI2Mjc1MSBDODYuMDEzNjcwMyw1Ni44MjYyNzUxIDcxLjIyMjU3MDYsNDguMjQ3OTU3MiA3MC42ODI2OTYyLDQ3Ljg3MDg0NDQgQzcwLjE0MjY4NDMsNDcuNDkzNzMxNiA3MC4xMzAxNjQzLDQ2LjkwNjM3NzQgNzAuMTMwMTY0Myw0Ni45MDYzNzc0IEM3MC4xMzAxNjQzLDQ2LjkwNjM3NzQgNzAuMTQ1MDIzMiwyOS45MTk5MTc0IDcwLjEzMDE2NDMsMjkuMTMzMzM2NCBDNzAuMTE1MzA1MywyOC4zNDY2MTc3IDcxLjA5Njk1NzYsMjcuNzU1NTQ2MSA3MS4wOTY5NTc2LDI3Ljc1NTU0NjEgTDg1Ljg3NTUzNzMsMTkuMjEzNDM4NyBDODYuNzg1MzcxNCwxOC43MzMyMDE2IDg3LjY3MTEyODYsMTkuMjEzNDM4NyA4Ny42NzExMjg2LDE5LjIxMzQzODcgQzg3LjY3MTEyODYsMTkuMjEzNDM4NyAxMDAuNzI2NjIzLDI2LjgwMjA5MzcgMTAyLjE3MzQ0MiwyNy42MTc3MjU3IEMxMDMuNTkxNTA3LDI4LjI5MTk1NzcgMTAzLjQxNjUwMiwyOS42ODQzNDI0IDEwMy40MTY1MDIsMjkuNjg0MzQyNCBDMTAzLjQxNjUwMiwyOS42ODQzNDI0IDEwMy40MjUzMDcsNDQuNzUxOTE5MiAxMDMuNDE2NTAyLDQ2LjIxNzU1MTEgTDEwMy40MTY1MDIsNDYuMjE3NTUxMSBaIE05Ny41MTYwMTA1LDI5LjE2OTEzMzkgQzk0LjQ5MDAxNzMsMjcuNDI3NDQ4MyA4Ny4zNjE1ODQyLDIzLjI5NzEwNjMgODcuMzYxNTg0MiwyMy4yOTcxMDYzIEM4Ny4zNjE1ODQyLDIzLjI5NzEwNjMgODYuNjY2MTAzOSwyMi45MjEyMzI2IDg1Ljk1MTc3NDcsMjMuMjk3MTA2MyBMNzQuMzQ4NzQwNiwyOS45ODIxNSBDNzQuMzQ4NzQwNiwyOS45ODIxNSA3My41NzgwMDI1LDMwLjQ0NDkwMTQgNzMuNTg5Njk3LDMxLjA2MDQ4MDUgQzczLjYwMTM5MTUsMzEuNjc2MDU5NyA3My41ODk2OTcsNDQuOTY5ODcwOCA3My41ODk2OTcsNDQuOTY5ODcwOCBDNzMuNTg5Njk3LDQ0Ljk2OTg3MDggNzMuNTk5NDY1NCw0NS40Mjk1OTMyIDc0LjAyMzQ5NTEsNDUuNzI0NjQ3MiBDNzQuNDQ3Mzg3Myw0Ni4wMTk3MDExIDg2LjA2MDE4OTgsNTIuNzMzMjQ1MSA4Ni4wNjAxODk4LDUyLjczMzI0NTEgQzg2LjA2MDE4OTgsNTIuNzMzMjQ1MSA4Ni41MzgxNTIsNTMuMDM5MTc1OSA4Ny4yNTMwMzE1LDUyLjczMzI0NTEgQzg3Ljk3NDY1MjYsNTIuMzMyNzI2MiA5OS4wNzMwMzM1LDQ1Ljk0MDI1ODIgOTkuMDczMDMzNSw0NS45NDAyNTgyIEM5OS4wNzMwMzM1LDQ1Ljk0MDI1ODIgOTkuNzE2MjMyNSw0NS42NjQ4OTI5IDk5LjcyMzY2MTksNDQuNDMwNzA1NiBDOTkuNzI1NzI1Nyw0NC4wNzQ3OTU5IDk5LjcyNjU1MTIsNDIuNjkzMjg4MSA5OS43MjY2ODg3LDQwLjk1NzUyMjkgTDg2LjY2MDA1MDIsNDguODc1MjM5NCBMODYuNjYwMDUwMiw0NS44NDYyMjEgQzg2LjY2MDA1MDIsNDQuNjAyMTIwNSA4Ny42MjMxMjg5LDQzLjc4MDk4MTEgODcuNjIzMTI4OSw0My43ODA5ODExIEw5OS4xODA3NjA3LDM2LjgxNjU3OTMgQzk5LjYxNjg5NzgsMzYuMzYxMTI1MSA5OS43MDY4NzY4LDM1LjYzMTU0NDcgOTkuNzI1NDUwNSwzNS4zNTU2Mjg3IEM5OS43MjUwMzc4LDM0LjA5MDQ2MjcgOTkuNzI0NDg3NCwzMi45ODUyODQxIDk5LjcyNDA3NDcsMzIuMjg1MTY3OCBMODYuNjYwMDUwMiw0MC4yMDEyMzIxIEw4Ni42NjAwNTAyLDM3LjAzNDUzMSBDODYuNjYwMDUwMiwzNS43OTA0MzA1IDg3LjQ4NTU0NjIsMzUuMjQ0NjU2NCA4Ny40ODU1NDYyLDM1LjI0NDY1NjQgTDk3LjUxNjAxMDUsMjkuMTY5MTMzOSBaIiBpZD0iU2hhcGUtQ29weSI+PC9wYXRoPgogICAgICAgIDwvZz4KICAgIDwvZz4KPC9zdmc+">
                <!--文字logo-->
                <span>后台</span>
            </div>
            <el-menu
                class="menu"
                :default-active="onRoutes"
                :collapse="isCollapse"
                style="border: none;"
                text-color="rgb(191, 203, 217)"
                background-color="rgb(48, 65, 86)"
                active-text-color="rgb(64, 158, 255)"
                unique-opened
                router>
                <sidebar-item v-for="item in routers" :key="item.path" :item='item' ></sidebar-item>
            </el-menu>
        </el-scrollbar>

        <el-container class="container-box" v-bind:class="{'slide-hide': isCollapse, 'slide-in-left': menuShow}">
            <el-header class="header">
                <div class="header-left">
                    <div class="header-toggle" @click="menuShow = !menuShow; showSideBar()">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                    <div class="slide-toggle" :class="{'slide-toggle-open': isCollapse}" @click="toggleSideBar">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                </div>
                <div class="header-tabs-box">
                    <el-breadcrumb class="app-levelbar" separator="/">
                        <el-breadcrumb-item v-for="(item,index)  in levelList" :key="item.path">
                        <span v-if='item.redirect==="noredirect"||index==levelList.length-1'
                              class="no-redirect">{{item.name}}</span>
                            <router-link v-else :to="item.redirect||item.path">{{item.name}}</router-link>
                        </el-breadcrumb-item>
                    </el-breadcrumb>
                </div>

                <div class="header-right">
                    <el-dropdown trigger="click">
                        <!--<i class="el-icon-setting" style="margin-right: 15px"></i>-->
                        <span>{{username}}<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item><span @click="handlePassword">修改密码</span></el-dropdown-item>
                            <el-dropdown-item><span @click="loginOut">退出登录</span></el-dropdown-item>
                            <el-dropdown-item disabled divided>主题切换</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>


                <!--新增界面-->
                <el-dialog title="修改密码" :visible.sync="passwordFormVisible" width="85%" top="5vh">
                    <el-form :model="passwordFormData" :rules="passwordFormDataRules" ref="passwordFormData">
                        <el-form-item label="原始密码" prop="old_password">
                            <el-input type="password" v-model="passwordFormData.old_password" auto-complete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="新密码" prop="new_password">
                            <el-input type="password" v-model="passwordFormData.new_password" auto-complete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="确认密码" prop="check_new_password">
                            <el-input type="password" v-model="passwordFormData.check_new_password" auto-complete="off"></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click.native="passwordFormVisible = !passwordFormVisible">取消</el-button>
                        <el-button type="primary" @click.native="addSubmit('passwordFormData')" :loading="passwordLoading">提交</el-button>
                    </div>
                </el-dialog>

            </el-header>

            <!--遮板-->
            <div class="main-mask"
                 v-show="menuShow"
                 @click="menuShow = !menuShow"></div>

            <el-main class="main">

                <transition name="move" mode="out-in">
                    <router-view></router-view>
                </transition>
            </el-main>
        </el-container>
    </el-container>

</template>


<script>
import { mapGetters } from "vuex";
import SidebarItem from "./SidebarItem.vue";
import { password } from "../../api/auth/login";
import { getAdminId } from "../../utils/auth";
export default {
    data() {
        let validatePass = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("请输入密码"));
            } else {
                if (this.passwordFormData.check_new_password !== "") {
                    this.$refs.passwordFormData.validateField(
                        "check_new_password"
                    );
                }
                callback();
            }
        };
        let validatePass2 = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("请再次输入密码"));
            } else if (value !== this.passwordFormData.new_password) {
                callback(new Error("两次输入密码不一致!"));
            } else {
                callback();
            }
        };
        return {
            menuShow: false,
            levelList: null,
            passwordLoading: false,
            passwordFormVisible: false,
            passwordFormData: {
                old_password: "",
                new_password: "",
                check_new_password: ""
            },
            passwordFormDataRules: {
                old_password: [
                    {
                        required: true,
                        message: "请输入原始密码",
                        trigger: "blur"
                    }
                ],
                new_password: [
                    {
                        required: true,
                        message: "请输入新密码",
                        trigger: "blur"
                    },
                    { validator: validatePass, trigger: "blur" }
                ],
                check_new_password: [
                    {
                        required: true,
                        message: "请再次输入密码",
                        trigger: "blur"
                    },
                    { validator: validatePass2, trigger: "blur" }
                ]
            }
        };
    },
    components: {
        SidebarItem
    },
    computed: {
        ...mapGetters({
            routers: "routers"
        }),
        onRoutes() {
            return this.$route.path;
        },
        username() {
            let username = this.$store.state.admin.userName;
            return !username ? this.name : username;
        },
        isCollapse() {
            return this.$store.state.app.sidebar.opened;
        }
    },
    mounted() {},
    methods: {
        toggleSideBar() {
            this.$store.dispatch("ToggleSideBar");
        },
        showSideBar() {
            this.$store.dispatch("ShowSideBar");
        },
        getBreadcrumb() {
            let matched = this.$route.matched.filter(item => item.name);
            const first = matched[0];
            if (first && (first.name !== "首页" || first.path !== "")) {
                matched = [{ name: "首页", path: "/" }].concat(matched);
            }
            this.levelList = matched;
        },
        loginOut() {
            this.$confirm("此操作将退出系统, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            })
                .then(() => {
                    this.$store.dispatch("loginOut").then(() => {
                        location.reload(); // 为了重新实例化vue-router对象 避免bug
                    });
                })
                .catch(() => {
                    this.$message({
                        type: "error",
                        message: "操作失败"
                    });
                });
        },
        // 显示修改密码界面
        handlePassword() {
            this.passwordFormVisible = true;
            this.passwordFormData = {
                old_password: "",
                new_password: "",
                check_new_password: ""
            };
        },
        addSubmit(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    this.passwordLoading = true;
                    let data = Object.assign({}, this.passwordFormData);
                    data.adminId = getAdminId();
                    password(data)
                        .then(res => {
                            this.passwordLoading = false;
                            if (res.code) {
                                this.$message({
                                    message: res.message,
                                    type: "error"
                                });
                            } else {
                                this.$message({
                                    message: "修改成功",
                                    type: "success"
                                });
                                // 刷新表单
                                this.$refs["passwordFormData"].resetFields();
                                this.passwordFormVisible = false;
                                this.$store.dispatch("loginOut").then(() => {
                                    location.reload(); // 为了重新实例化vue-router对象 避免bug
                                });
                            }
                        })
                        .catch(() => {
                            this.$message({
                                type: "error",
                                message: "操作失败"
                            });
                        });
                }
            });
        }
    },
    created() {
        this.getBreadcrumb();
    },
    watch: {
        $route() {
            this.getBreadcrumb();
        }
    }
};
</script>


<style type="text/scss" lang="scss">
@import "../../styles/mixin";
.el-header {
    /*background-color: #eef1f6;*/
    color: #333;
    padding: 0 10px;
}

.el-aside {
    color: #333;
}

.el-main {
    position: relative;
    padding: 0 10px 10px;
    background-color: #fff;
    margin-left: 7px;
    margin-top: 5px;
}
.main-mask {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 200%;
    z-index: 100;
    background-color: #000;
    opacity: 0.3;
}

.move-enter-active,
.move-leave-active {
    transition: opacity 0.5s;
}

.move-enter,
.move-leave {
    opacity: 0;
}

.wrapper {
    height: 100%;
    overflow-y: hidden;
    background-color: #f5f7f9;
}

.container-box {
    height: 100%;
    transition: -webkit-transform 0.3s ease-in-out;
    transition: transform 0.3s ease-in-out, -webkit-transform 0.3s ease-in-out;
    overflow-y: auto;
}

.container-left {
    background-color: rgb(48, 65, 86);
    transition: left 0.23s ease-in-out;
    height: 100%;
    flex: 0 0 auto;
}
.container-left-wrap {
    overflow-x: hidden !important;
}

.container-left .svg-icon {
    font-size: 20px;
    margin-right: 5px;
}

.container-left:not(.slide-hide) {
    width: 200px;
}

.container-left.slide-hide {
    .logo img {
        display: none;
    }
    .logo span {
        display: inline-block;
    }
}

.menu {
    width: 100% !important;
}

/*.menu::-webkit-scrollbar{*/
/*display: none;*/
/*}*/

.header {
    position: relative;
    text-align: left;
    font-size: 12px;
    line-height: $header-height;
    border-bottom: 1px solid #d8dce5;
    background-color: #fff;
}

.logo {
    display: block;
    width: 100%;
    line-height: 80px;
    text-align: center;
    color: #fff;
    transition: display 0.7s ease-in-out;
    img {
        vertical-align: middle;
    }
    span {
        display: none;
    }
}

.header-left {
    display: inline-block;
    height: $header-height;
    overflow: hidden;
}

.header-tabs-box {
    display: inline-block;
    height: $header-height;
    max-width: 60%;
    overflow: hidden;
}

.header-right {
    display: flex;
    width: 80px;
    height: 100%;
    margin-left: 10px;
    float: right;
    .el-dropdown {
        display: flex;
        cursor: pointer;
    }
    span {
        display: inline-block;
        @include text-overflow;
    }
    i {
        line-height: $header-height;
        margin-top: 2px;
    }
}

.app-levelbar {
    margin-left: 20px;
    line-height: 57px !important;
}

/*宽屏时出现*/
.slide-toggle {
    display: inline-block;
    height: $header-height;
    line-height: $header-height;
    text-align: center;
    cursor: pointer;
}

.slide-toggle span {
    display: inline-block;
    width: 2px;
    height: 12px;
    background-color: rgba(135, 141, 153, 0.8);
    margin-left: 5px;
}
.slide-toggle-open span {
    display: block;
    margin-left: 4px;
    margin-top: 4px;
    width: 16px;
    height: 2px;
}
.slide-toggle-open {
    padding-top: 17px;
}

@media screen and (min-width: 768px) {
    .main-mask {
        display: none;
    }
}

/*.slide-hide {*/
/*width: 64px !important;*/
/*}*/

@media screen and (max-width: 768px) {
    .main {
        overflow-y: scroll !important;
        -webkit-overflow-scrolling: touch;
    }

    .slide-hide.slide-in-left {
        -webkit-transform: translate3d(64px, 0, 0) !important;
        transform: translate3d(64px, 0, 0) !important;
    }
    .container-left.slide-hide.slide-in-left {
        left: -64px;
        width: 64px;
    }
    .container-left.slide-in-left {
        left: 0;
    }
    .container-left {
        margin-right: 0;
        opacity: 1;
        position: absolute !important;
        top: 0;
        left: -225px;
        z-index: 10;
        padding: 0;
    }

    .container-box.slide-in-left {
        min-width: 0;
        opacity: 1;
        -webkit-transform: translate3d(187px, 0, 0);
        transform: translate3d(187px, 0, 0);
        overflow: initial;
    }
    .header-toggle {
        display: block;
        /*background-color: #26a2ff;*/
        border-radius: 4px;
        /*border: 1px solid #fff;*/
        height: 40px;
        margin: 10px 0;
        padding: 2px 6px;
        outline: none;
        width: 40px;
        z-index: 10;
    }

    .header-toggle span {
        display: block;
        width: 100%;
        height: 4px;
        margin: 5px auto;
        background-color: rgba(135, 141, 153, 0.8);
    }

    .logo {
        display: none;
    }
    .slide-toggle {
        display: none;
    }
}
</style>
