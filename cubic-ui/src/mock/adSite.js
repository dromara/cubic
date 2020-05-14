const index = {
    data: {
        total: 1,
        list: [
            {
                siteId: 1,
                siteName: "测试12222111",
                describe: "支持android和ios",
                adIds: [4, 1],
                ads: [
                    {
                        adId: 4,
                        title: "fffxxxx",
                        describe: "vvv",
                        status: 0
                    },
                    {
                        adId: 1,
                        title: "阿范德萨",
                        describe: "撒地方士大夫",
                        status: 1
                    }
                ]
            },
            {
                siteId: 2,
                siteName: "车是是是",
                describe: "车是是是发发发",
                adIds: [1, 2, 4],
                ads: [
                    {
                        adId: 1,
                        title: "阿范德萨",
                        describe: "撒地方士大夫",
                        status: 1
                    },
                    {
                        adId: 2,
                        title: "ffff",
                        describe: "fff",
                        status: 0
                    },
                    {
                        adId: 4,
                        title: "fffxxxx",
                        describe: "vvv",
                        status: 0
                    }
                ]
            }
        ]
    }
};

const adList = {
    data: {
        total: 1,
        list: [
            {
                adId: 1,
                title: "阿范德萨",
                describe: "撒地方士大夫",
                status: 1
            },
            {
                adId: 6,
                title: "侧嗯嗯得到的",
                describe: "车次多的的",
                status: 1
            },
            {
                adId: 7,
                title: "hi额接口数据东方丽景",
                describe: "反倒是打发士大夫撒",
                status: 1
            }
        ]
    }
};

const save = {
    data: {
        siteId: 2
    }
};

const edit = {
    code: 0,
    message: "success"
};

const del = {
    code: 0,
    message: "success"
};

export default {
    index,
    adList,
    save,
    edit,
    del
};
