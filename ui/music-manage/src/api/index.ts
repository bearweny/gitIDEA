import {deletes, get, getBaseURL, post} from './request'

const HttpManager = {
    // 获取图片信息
    attachImageUrl: (url) => url ? `${getBaseURL()}${url}` : '',

    // =======================> 管理员 API 完成
    // 是否登录成功
    login: ({name, password}) => post(`admin/login`, {
        name,
        password
    }),


    // =======================> 用户 API 完成
    // 返回所有用户
    // getAllUser: () => get(`user`),
    getAllUser: () => get(`consumer/list`),
    getAllUserPage: (currentPage, pageSize, keyword) =>
        post(`consumer/page`, { currentPage, pageSize, keyword }),
    // 返回指定ID的用户
    getUserOfId: (id) => get(`user/detail?id=${id}`),
    // 删除用户
    // deleteUser: (id) => get(`user/delete?id=${id}`),
    // 删除用户
    deleteUser: (id) => deletes(`consumer/delete/${id}`),
    // 批量删除用户
    batchDeleteUser: (ids) => deletes('consumer/batchDelete', ids),

    // =======================> 收藏列表 API 完成
    // 返回的指定用户ID收藏列表
    getCollectionOfUser: (userId, keyword) => post('collect/list', { userId, keyword }),
    // 删除收藏的歌曲
    // deleteCollection: (userId, songId) => deletes(`collection/delete?userId=${userId}&&songId=${songId}`),
    // 删除收藏记录（根据收藏 ID）
    deleteCollect: (id) => deletes(`collect/delete/${id}`),
    // 批量删除收藏歌曲
    // 批量删除收藏
    deleteBatchCollection: (userId, songIds) => post('collect/deleteBatch', { userId, songIds }),
    getCommentByType: (id, type) => get(`comment/type/${id}/${type}`),


    // =======================> 评论列表 API 完成
    // 获得指定歌曲ID的评论列表
    getCommentOfSongId: (songId) => get(`comment/song/detail?songId=${songId}`),
    // 获得指定歌单ID的评论列表

    getCommentOfSongListId: (songListId) => get(`comment/songList/detail?songListId=${songListId}`),
    // 删除评论
    deleteComment: (id) => get(`comment/delete?id=${id}`),

    // =======================> 歌手 API 完成
    // 返回所有歌手
    getAllSinger: () => get(`singer`),
    getAllSingerPage: (currentPage, pageSize, keyword) => post(`singer/page`,{currentPage, pageSize, keyword}),

    // 添加歌手
    setSinger: ({id,name, sex, birth, location, introduction}) => post(`singer/save`, {
        id,
        name,
        sex,
        birth,
        location,
        introduction
    }),
    // 更新歌手信息
    updateSingerMsg: ({id, name, sex, birth, location, introduction}) => post(`singer/save`, {
        id,
        name,
        sex,
        birth,
        location,
        introduction
    }),
    // 删除歌手
    deleteSinger: (id) => deletes(`singer/delete?id=${id}`),
    // 批量删除歌手
    batchDeleteSinger: (ids) => deletes('singer/batchDelete', ids),
    // =======================> 歌曲 API  完成
    // 返回所有歌曲
    getAllSong: () => get(`song`),
    // 返回指定歌手ID的歌曲
    getAllSongPage: (singerId, currentPage, pageSize, keyword) => post(`song/page`,{singerId, currentPage, pageSize, keyword}),

    getSongOfSingerId: (id) => get(`song/singer/detail?singerId=${id}`),
    // 返回的指定用户ID收藏列表
    getSongOfId: (id) => get(`song/detail?id=${id}`),
    // 返回指定歌手名的歌曲
    getSongOfSingerName: (id) => get(`song/singerName/detail?name=${id}`),
    // 更新歌曲信息
    updateSongMsg: ({id, singerId, name, introduction, lyric}) => post(`song/update`, {
        id,
        singerId,
        name,
        introduction,
        lyric
    }),
    updateSongUrl: (id) => `${getBaseURL()}/song/url/update?id=${id}`,
    updateSongImg: (id) => `${getBaseURL()}/song/img/update?id=${id}`,
    updateSongLrc: (id) => `${getBaseURL()}/song/lrc/update?id=${id}`,
    // 删除歌曲
    deleteSong: (id) => deletes(`song/delete?id=${id}`),

    // =======================> 歌单 API 完成
    // 添加歌单t
    setSongList: ({title, introduction, style}) => post(`songList/add`, {title, introduction, style}),
    // 获取全部歌单
    // getSongList: () => get(`songList`),
    getAllSongList: () => get(`songList/list`),
    // 更新歌单信息
    updateSongListMsg: ({id, title, introduction, style}) => post(`songList/update`, {id, title, introduction, style}),
    // 删除歌单
    deleteSongList: (id) => deletes(`songList/delete?id=${id}`),// POST JSON body 方式发送 DELETE 请求
    // 批量删除歌单
    batchDeleteSongList: (ids) => post('songList/batchDelete', ids),
    // 编辑歌单
    updateSongList: (data) => post('songList/update', data),

    // =======================> 歌单歌曲 API 完成
    // 给歌单添加歌曲
    getAllSongListPage: (currentPage, pageSize, keyword) =>
        post('songList/page', { currentPage, pageSize, keyword }),


    setListSong: ({songId,songListId}) => post(`listSong/add`, {songId,songListId}),
    // 返回歌单里指定歌单ID的歌曲
    getListSongOfSongId: (songListId) => get(`listSong/detail?songListId=${songListId}`),
    // 删除歌单里的歌曲
    deleteListSong: (songId) => get(`listSong/delete?songId=${songId}`)

}

export {HttpManager}
