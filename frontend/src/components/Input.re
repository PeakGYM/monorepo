[@react.component] [@bs.module "antd"]
external make:
  (
    ~placeholder: string,
    ~value: string,
    ~htmlType: string,
    ~onChange: ReactEvent.Form.t => unit
  ) =>
  React.element =
  "Input";