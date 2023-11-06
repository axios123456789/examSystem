export function isEmpty(obj){
    if ((typeof obj === 'string')){
        return !obj ||  obj.replace(/\s+/g, "") === ""
    }else {
        return (!obj || JSON.stringify(obj) === "{}" || obj.length === 0)
    }
}