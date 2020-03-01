type config = {
  .
  "content": string,
  "icon": React.element,
  "top": int,
};

[@bs.module "antd"] [@bs.scope "message"]
external success: config => unit = "success";