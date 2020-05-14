function pluralize(time, label) {
    if (time === 1) {
        return time + label;
    }
    return time + label + "s";
}
export function timeAgo(time) {
    const between = Date.now() / 1000 - Number(time);
    if (between < 3600) {
        return pluralize(~~(between / 60), " minute");
    } else if (between < 86400) {
        return pluralize(~~(between / 3600), " hour");
    } else {
        return pluralize(~~(between / 86400), " day");
    }
}

/**
 * @return {string}
 */
export function formatDateStr(date_str, fmt) {
    let date = new Date(date_str);
    if (isNaN(date.getDate())) {
        return "";
    }
    const o = {
        "M+": date.getMonth() + 1, //月份
        "d+": date.getDate(), //日
        "h+": date.getHours(), //小时
        "m+": date.getMinutes(), //分
        "s+": date.getSeconds(), //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        S: date.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(
            RegExp.$1,
            (date.getFullYear() + "").substr(4 - RegExp.$1.length)
        );
    for (let k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(
                RegExp.$1,
                RegExp.$1.length === 1
                    ? o[k]
                    : ("00" + o[k]).substr(("" + o[k]).length)
            );
    return fmt;
}

export function parseTime(time, cFormat) {
    if (arguments.length === 0) {
        return null;
    }

    if ((time + "").length === 10) {
        time = +time * 1000;
    }

    const format = cFormat || "{y}-{m}-{d} {h}:{i}:{s}";
    let date;
    if (typeof time === "object") {
        date = time;
    } else {
        date = new Date(parseInt(time));
    }
    const formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes(),
        s: date.getSeconds(),
        a: date.getDay()
    };
    const timeStr = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
        let value = formatObj[key];
        if (key === "a")
            return ["一", "二", "三", "四", "五", "六", "日"][value - 1];
        if (result.length > 0 && value < 10) {
            value = "0" + value;
        }
        return value || 0;
    });
    return timeStr;
}

export function formatTime(time, option) {
    time = +time * 1000;
    const d = new Date(time);
    const now = Date.now();

    const diff = (now - d) / 1000;

    if (diff < 30) {
        return "刚刚";
    } else if (diff < 3600) {
        // less 1 hour
        return Math.ceil(diff / 60) + "分钟前";
    } else if (diff < 3600 * 24) {
        return Math.ceil(diff / 3600) + "小时前";
    } else if (diff < 3600 * 24 * 2) {
        return "1天前";
    }
    if (option) {
        return parseTime(time, option);
    } else {
        return (
            d.getMonth() +
            1 +
            "月" +
            d.getDate() +
            "日" +
            d.getHours() +
            "时" +
            d.getMinutes() +
            "分"
        );
    }
}

/*  数字 格式化 */
export function nFormatter(num, digits) {
    const si = [
        { value: 1e18, symbol: "E" },
        { value: 1e15, symbol: "P" },
        { value: 1e12, symbol: "T" },
        { value: 1e9, symbol: "G" },
        { value: 1e6, symbol: "M" },
        { value: 1e3, symbol: "k" }
    ];
    for (let i = 0; i < si.length; i++) {
        if (num >= si[i].value) {
            return (
                (num / si[i].value + 0.1)
                    .toFixed(digits)
                    .replace(/\.0+$|(\.[0-9]*[1-9])0+$/, "$1") + si[i].symbol
            );
        }
    }
    return num.toString();
}

export function html2Text(val) {
    const div = document.createElement("div");
    div.innerHTML = val;
    return div.textContent || div.innerText;
}

export function toThousandslsFilter(num) {
    return (+num || 0)
        .toString()
        .replace(/^-?\d+/g, m => m.replace(/(?=(?!\b)(\d{3})+$)/g, ","));
}

/**
 * 格式化文件大小
 * @param value
 * @returns {*}
 */
export function renderSize(value) {
    if (!value || value === null || value === "") {
        return "";
    }
    let unitArr = ["B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];
    let srcsize = parseFloat(value);
    let index = Math.floor(Math.log(srcsize) / Math.log(1024));
    let size = srcsize / Math.pow(1024, index);
    size = size.toFixed(0); // 保留的小数位数
    return size + unitArr[index];
}
