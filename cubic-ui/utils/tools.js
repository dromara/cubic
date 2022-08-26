/**
 * 将'', null以及undefined参数过滤掉
 * @param {*} obj 过滤的参数对象
 */
function interceptParams(obj) {
  let param = {}
  if (obj === null || obj === undefined || obj === '') {
    return param
  }
  for (let key in obj) {
    if (obj[key] !== null && obj[key] !== undefined && obj[key] !== '') {
      if (typeof (obj[key]) == 'string') {
        obj[key] = obj[key].trim()
      }
      param[key] = obj[key]
    }
  }
  return param
}

/**
 * 创建 blob & 下载文件
 */
function createBlobAndExport(data, fileName) {
  let blob = new Blob([data], {
    type: 'application/vnd.ms-excel;charset=UTF-8'
  })
  let href = window.URL.createObjectURL(blob)
  let file = fileName
  let downloadElement = document.createElement('a')
  downloadElement.href = href
  downloadElement.download = file
  downloadElement.target = '_blank'
  downloadElement.click()
  window.URL.revokeObjectURL(href)
}

export {
  interceptParams,
  createBlobAndExport
}
