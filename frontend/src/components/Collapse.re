[@react.component] [@bs.module "antd"]
external make:
  (~children: React.element, ~activeKey: array(string), ~bordered: bool) =>
  React.element =
  "Collapse";

module Panel = {
  [@react.component] [@bs.module "antd"] [@bs.scope "Collapse"]
  external make:
    (~key: string, ~children: React.element, ~extra: React.element) =>
    React.element =
    "Panel";
};