/**
 * Created by lk on 18/4/28.
 */

// 资源列表
const index = {
    data: {
        total: 10,
        list: [
            {
                id: 1,
                type: 0,
                filename: "home_many.png",
                path:
                    "resources/image/20180529/0b429c0d31011ec46f2328ac0ca29c15.png",
                size: 9705,
                ext: "png",
                createTime: false,
                url:
                    "http://www.test.com/uploads/resources/image/20180529/0b429c0d31011ec46f2328ac0ca29c15.png"
            },
            {
                id: 2,
                type: 0,
                filename: "homeManyPlay.png",
                path:
                    "resources/image/20180529/2988116bffc258321a48909f48841028.png",
                size: 9665,
                ext: "png",
                createTime: 1527601051,
                url:
                    "http://www.test.com/uploads/resources/image/20180529/2988116bffc258321a48909f48841028.png"
            }
        ]
    }
};

// 上传资源
const add = {
    data: {
        id: 11
    }
};

export default {
    index,
    add
};
