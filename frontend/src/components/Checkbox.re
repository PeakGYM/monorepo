[@react.component] [@bs.module "antd"]
external make:
  (
    ~onChange: unit => unit,
    ~checked: bool=?,
    ~children: React.element,
    ~disabled: bool=?
  ) =>
  React.element =
  "Checkbox";