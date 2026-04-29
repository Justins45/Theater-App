import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import ButtonComponent from '@/components/Button.vue'

describe('ButtonComponent.vue', () => {
  it('renders a button with the correct text', () => {
    const wrapper = mount(ButtonComponent)

    expect(wrapper.find('button').text()).toBe('Click me Button!')
  })
})
